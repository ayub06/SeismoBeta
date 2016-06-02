package com.pkm.proyek.seismoalpha.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.laporan.tim.InputActivity;
import com.pkm.proyek.seismoalpha.laporan.tim.Laporan;
import com.pkm.proyek.seismoalpha.laporan.LaporanActivity;
import com.pkm.proyek.seismoalpha.laporan.umum.InputUmumActivity;
import com.pkm.proyek.seismoalpha.laporan.umum.LaporanUmum;
import com.pkm.proyek.seismoalpha.main.Gempa;
import com.pkm.proyek.seismoalpha.main.GempaAdapter;
import com.pkm.proyek.seismoalpha.main.MainActivity;
import com.pkm.proyek.seismoalpha.maps.MapsActivity;
import com.pkm.proyek.seismoalpha.pelapor.LoginActivity;
import com.pkm.proyek.seismoalpha.pelapor.Pelapor;
import com.pkm.proyek.seismoalpha.rehabrekon.kerusakan.InputKerusakanActivity;
import com.pkm.proyek.seismoalpha.rehabrekon.korban.InputKorbanActivity;
import com.pkm.seismosense.backend.kerusakanApi.KerusakanApi;
import com.pkm.seismosense.backend.kerusakanLainApi.KerusakanLainApi;
import com.pkm.seismosense.backend.korbanApi.KorbanApi;
import com.pkm.seismosense.backend.laporanUmumApi.LaporanUmumApi;
import com.pkm.seismosense.backend.myApi.MyApi;
import com.pkm.seismosense.backend.pelaporApi.PelaporApi;
import com.pkm.seismosense.backend.laporanApi.LaporanApi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class loadFromAPI extends AsyncTask<Pair<Context, String>, Void, String> {

    public static int sync_mode;
    public static final int SYNC_MODE_LOGIN=1;
    public static final int SYNC_MODE_GET_LAPORAN=2;
    public static final int SYNC_MODE_POST_LAPORAN=3;
    public static final int SYNC_MODE_POST_KORBAN_RR=4;
    public static final int SYNC_MODE_POST_KERUSAKAN_RR=5;
    public static final int SYNC_MODE_POST_KERUSAKAN_LAIN_RR=6;
    public static final int SYNC_MODE_POST_LAPORAN_UMUM=7;
    public static final int SYNC_MODE_GET_LAPORAN_UMUM=8;

    public static int from;
    public static final int MAIN_ACTIVITY=-1;
    public static final int LAPORAN_ACTIVITY=-2;

/*    public static final String URL_ROOT="http://10.0.2.2:8080/_ah/api/";*/
    public static final String URL_ROOT="https://seismosense-1261.appspot.com/_ah/api/";
    public static final String URL_ROOT_TANDINGAN="http://10.0.2.2:8080/_ah/api/";

    private static MyApi myApiService   =   null;
    private static PelaporApi loginApi  =   null;
    private static LaporanApi laporanApi=   null;
    private static KorbanApi korbanApi  =   null;
    private static KerusakanApi kerusakanApi  =   null;
    private static KerusakanLainApi kerusakanLainApi  =   null;
    private static LaporanUmumApi laporanUmumApi=   null;

    private Context context;
    private String username;
    private Pair<Context, String>[] rehabKerusakanLainSync;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        switch (sync_mode) {
            case SYNC_MODE_LOGIN:
                return loginSync(params);
            case SYNC_MODE_GET_LAPORAN:
                return getLaporanSync(params);
            case SYNC_MODE_POST_LAPORAN:
                return setLaporanSync(params);
            case SYNC_MODE_POST_KORBAN_RR:
                return setRehabKorbanSync(params);
            case SYNC_MODE_POST_KERUSAKAN_RR:
                return setRehabKerusakanSync(params);
            case SYNC_MODE_POST_KERUSAKAN_LAIN_RR:
                return setRehabKerusakanLainSync(params);
            case SYNC_MODE_POST_LAPORAN_UMUM:
                return setLaporanUmumSync(params);
            case SYNC_MODE_GET_LAPORAN_UMUM:
                return getLaporanUmumSync(params);
            default:
                Log.d("loadFromAPI", "DEFAULT");
                return "NOTHING";
        }
    }

    @Override
    protected void onPreExecute() {
        switch (sync_mode){
            case SYNC_MODE_LOGIN:
                LoginActivity.startLoad();
                break;
            case SYNC_MODE_POST_LAPORAN:
                InputActivity.loadStart();
                break;
            default:
                break;
        }
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("RESULT RESPONSE : ", result);
        if (result.contains("Unable to resolve host")){
            Toast.makeText(context,"Koneksi Internet Gagal", Toast.LENGTH_SHORT).show();
        }

        if (result.contains("timeout")){
            Toast.makeText(context,"Timeout", Toast.LENGTH_SHORT).show();
            if(from==LAPORAN_ACTIVITY){
                //LaporanActivity.loadStop();
            }else if (from==MAIN_ACTIVITY){
                MainActivity.stopLoading();
            }else {
                InputActivity.loadStop();
            }
        }
        if (result.equals("1")){
            switch (sync_mode){
                case SYNC_MODE_LOGIN:
                    Bundle bundle=new Bundle();
                    bundle.putString("username", /*"Frans"*/this.username);
                    Intent intent=new Intent(context, MainActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    break;
                case SYNC_MODE_POST_LAPORAN:
                    //Bundle bundle1=new Bundle();
                    //bundle1.putInt("gempaid", Integer.parseInt(InputActivity.laporanSave.getGempaId()));
                    //Intent intent1=new Intent(context, LaporanActivity.class);
                    //intent1.putExtras(bundle1);
                    InputActivity.loadStop();
                    InputActivity.success(context);
                    break;
                case SYNC_MODE_GET_LAPORAN:
                    if (from==MAIN_ACTIVITY){
                        Bundle bundleX=new Bundle();
                        bundleX.putInt(MapsActivity.MODE,MapsActivity.DISPLAY_GEMPA);
                        bundleX.putInt(MapsActivity.ID_GEMPA_OR_LAPORAN, GempaAdapter.indexGempaMain);
                        Intent intentX=new Intent(new Intent(context, MapsActivity.class));
                        intentX.putExtras(bundleX);
                        MainActivity.stopLoading();
                        context.startActivity(intentX);
                    }else {
                        Log.d("RESULT GET LAPORAN", result);
                        LaporanActivity.showLaporanlist(false);
                    }

                    break;
                case SYNC_MODE_POST_KORBAN_RR:

                    //Intent intent1=new Intent(context, RehabRekonActivity.class);
                    //context.startActivity(intent1);
                    break;
                case SYNC_MODE_POST_KERUSAKAN_RR:
                    loadFromAPI.sync_mode=loadFromAPI.SYNC_MODE_POST_KERUSAKAN_LAIN_RR;
                    new loadFromAPI().execute(
                            new Pair<Context, String>(context,"NOTHING")
                    );

                    break;
                case SYNC_MODE_POST_KERUSAKAN_LAIN_RR:
                    Log.d("KERUSAKAN LAIN DB","OK");
                    break;
                case SYNC_MODE_POST_LAPORAN_UMUM:
                    //Bundle bundle1=new Bundle();
                    //bundle1.putInt("gempaid", Integer.parseInt(InputActivity.laporanSave.getGempaId()));
                    //Intent intent1=new Intent(context, LaporanActivity.class);
                    //intent1.putExtras(bundle1);
                    InputUmumActivity.loadStop();
                    InputUmumActivity.success(context);
                    break;
                case SYNC_MODE_GET_LAPORAN_UMUM:
                    Log.d("RESULT GET LAPORAN", result);
                    break;
                default:
                    Log.d("RESULT RESPONSE", result+"  1");
                    break;
            }
        }else {
            switch (sync_mode) {
                case SYNC_MODE_LOGIN:
                    LoginActivity.stopLoad();
                    LoginActivity.wrongAcc();
                    break;
                case SYNC_MODE_POST_LAPORAN:
                    InputActivity.loadStop();
                    InputActivity.failed(context);
                default:
                    break;
            }

            Log.d("RESULT RESPONSE", result);
        }
    }

    private String loginSync(Pair<Context, String>... params){
        if(loginApi == null) {  // Only do this once
            PelaporApi.Builder builder = new PelaporApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL_ROOT)
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })*/;
            // end options for devappserver

            loginApi = builder.build();
        }

        context = params[0].first;
        String username = params[0].second;
        String pw= params[1].second;
        this.username=username;
        Log.d("user",username);
        Log.d("pw", pw);

        try {
            return loginApi.loginPelapor(username,pw).execute().getEmail(); //Email diisi Response di Backend
        } catch (IOException e) {
            return e.getMessage();
        }

        //OFFLINE TRY
        //return "1";
    }

    private String getLaporanSync(Pair<Context, String>[] params) {
        if(laporanApi== null) {  // Only do this once
            LaporanApi.Builder builder = new LaporanApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL_ROOT)
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })*/;
            laporanApi= builder.build();
        }
        context = params[0].first;

        Log.d("getLaporanSync","1");
        try {
            Laporan.laporanArrayList=new ArrayList<>();
            List<com.pkm.seismosense.backend.laporanApi.model.Laporan> laporanList=
                    laporanApi.list(String.valueOf(LaporanActivity.indexGempa)).execute().getItems();

            if (laporanList!=null) {
                Log.d("TRY PARSE", String.valueOf(laporanList.size()));
                for (int i = 0; i < laporanList.size(); i++) {
                    //Getting Laporan Time
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(laporanList.get(i).getId());

                    //Getting Pelapor Index
                    int indexPelapor=0;
                    for(int j = 0; j< Pelapor.pelaporArrayList.size(); j++){
                        if(Pelapor.pelaporArrayList.get(j).getUsername().equals(laporanList.get(i).getUsernamePelapor())){
                            indexPelapor=j;
                        }
                    }

                    //Getting foto
                    //Create Design Bitmap by getting the stream
                    byte[] bytestream=
                            android.util.Base64.decode(
                                    laporanList.get(i).getFoto().getBytes(),
                                    android.util.Base64.DEFAULT);

                    //Create new Laporan from Server
                    Laporan.laporanArrayList.add(new Laporan(
                            laporanList.get(i).getId(),
                            Gempa.gempaArrayList.get(Integer.parseInt(laporanList.get(i).getGempaId())),
                            Pelapor.pelaporArrayList.get(indexPelapor),
                            calendar,
                            new LatLng(laporanList.get(i).getLokasiLat(), laporanList.get(i).getLokasiLng()),
                            laporanList.get(i).getAlamat(),
                            laporanList.get(i).getJumlahKorban(),
                            laporanList.get(i).getLukaBerat(),
                            laporanList.get(i).getLukaRingan(),
                            laporanList.get(i).getRusakBerat(),
                            laporanList.get(i).getRusakRingan(),
                            BitmapFactory.decodeByteArray(bytestream, 0, bytestream.length)
                    ));
                }
            }
            return "1";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String setLaporanSync(Pair<Context, String>[] params) {
        if(laporanApi== null) {  // Only do this once
            LaporanApi.Builder builder = new LaporanApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL_ROOT)
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })*/;
            laporanApi= builder.build();
        }
        context = params[0].first;

        try {
            //Laporan.laporanArrayList=new ArrayList<>();
            //List<com.pkm.seismosense.backend.laporanApi.model.Laporan> laporanList=
            //        laporanApi.list().execute().getItems();

            String result= String.valueOf(laporanApi.insert(InputActivity.laporanSave).execute().getId());
            if (result.isEmpty()){
                return "0";
            }else {
                return "1";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String setRehabKorbanSync(Pair<Context, String>[] params) {
        if(korbanApi== null) {  // Only do this once
            KorbanApi.Builder builder = new KorbanApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL_ROOT)
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })*/;
            korbanApi= builder.build();
        }
        context = params[0].first;

        try {
            String result = "";
            for(int i=0;i<InputKorbanActivity.korbansBackEnd.size();i++) {
                result = String.valueOf(korbanApi.insert(InputKorbanActivity.korbansBackEnd.get(i)).execute().getId());
            }
            if (result.isEmpty()){
                return "0";
            }else {
                return "1";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String setRehabKerusakanSync(Pair<Context, String>[] params) {
        if(kerusakanApi== null) {  // Only do this once
            KerusakanApi.Builder builder = new KerusakanApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL_ROOT)
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })*/;
            kerusakanApi= builder.build();
        }
        context = params[0].first;

        try {
            String result = "";
            for(int i = 0; i< InputKerusakanActivity.kerusakansBackEnd.size(); i++) {
                result = String.valueOf(kerusakanApi.insert(InputKerusakanActivity.kerusakansBackEnd.get(i)).execute().getId());
            }

            if (result.isEmpty()){
                return "0";
            }else {
                return "1";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public String setRehabKerusakanLainSync(Pair<Context,String>[] params) {
        if(kerusakanLainApi== null) {  // Only do this once
            KerusakanLainApi.Builder builder = new KerusakanLainApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL_ROOT)
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })*/;
            kerusakanLainApi= builder.build();
        }
        context = params[0].first;

        try {
            String result = "";

            result = String.valueOf(kerusakanLainApi.insert(InputKerusakanActivity.kerusakanLainBackEnd).execute().getId());

            if (result.isEmpty()){
                return "0";
            }else {
                return "1";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String defaultSync(Pair<Context, String>... params){
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)

                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(URL_ROOT)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;
        Log.d("here",name);
        try {
            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String getLaporanUmumSync(Pair<Context, String>[] params) {
        if(laporanUmumApi== null) {  // Only do this once
            LaporanUmumApi.Builder builder = new LaporanUmumApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL_ROOT)
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })*/;
            laporanUmumApi= builder.build();
        }
        context = params[0].first;

        Log.d("KENE","1");
        try {
            LaporanUmum.laporanArrayList=new ArrayList<>();
            List<com.pkm.seismosense.backend.laporanUmumApi.model.LaporanUmum> laporanList=
                    laporanUmumApi.list(String.valueOf(LaporanActivity.indexGempa)).execute().getItems();

            if (laporanList!=null) {
                Log.d("TRY PARSE", String.valueOf(laporanList.size()));
                for (int i = 0; i < laporanList.size(); i++) {
                    //Getting Laporan Time
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(laporanList.get(i).getId());

                    //Getting foto
                    //Create Design Bitmap by getting the stream
                    byte[] bytestream=
                            android.util.Base64.decode(
                                    laporanList.get(i).getFoto().getBytes(),
                                    android.util.Base64.DEFAULT);

                    //Create new Laporan from Server
                    LaporanUmum.laporanArrayList.add(new LaporanUmum(
                            laporanList.get(i).getId(),
                            Gempa.gempaArrayList.get(Integer.parseInt(laporanList.get(i).getGempaId())),
                            laporanList.get(i).getNama(),
                            calendar,
                            new LatLng(laporanList.get(i).getLokasiLat(), laporanList.get(i).getLokasiLng()),
                            laporanList.get(i).getAlamat(),
                            laporanList.get(i).getJumlahKorban(),
                            laporanList.get(i).getLukaBerat(),
                            laporanList.get(i).getLukaRingan(),
                            laporanList.get(i).getRusakBerat(),
                            laporanList.get(i).getRusakRingan(),
                            BitmapFactory.decodeByteArray(bytestream, 0, bytestream.length)
                    ));

                    //Getting Pelapor Foto
                    new LoadProfileImage(i).execute(laporanList.get(i).getUrlFoto());

                }
            }
            return "1";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String setLaporanUmumSync(Pair<Context, String>[] params) {
        if(laporanUmumApi== null) {  // Only do this once
            LaporanUmumApi.Builder builder = new LaporanUmumApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(URL_ROOT)
                    /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    })*/;
            laporanUmumApi= builder.build();
        }
        context = params[0].first;

        try {

            //LOAD INPUT LAPORAN UMUM FOR SAVE (Tunggu Fata Dulu)
            String result= String.valueOf(laporanUmumApi.insert(InputUmumActivity.laporanSave).execute().getId());
            if (result.isEmpty()){
                return "0";
            }else {
                return "1";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {

        private int index;

        public LoadProfileImage(int index) {
            this.index = index;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            if(urldisplay.contains("https")){
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result==null){
                LaporanUmum.laporanArrayList.get(index).setFoto(
                        BitmapFactory.decodeResource(
                                LaporanActivity.activity.getResources(),
                                R.drawable.ic_account_circle_black_48dp)
                );
                Log.d("AKUN FOTO","DEFAULT");
            }else {
                LaporanUmum.laporanArrayList.get(index).setFoto(result);
                Log.d("AKUN FOTO", "ADA");
            }
            LaporanActivity.showLaporanlist(true);
        }
    }

}