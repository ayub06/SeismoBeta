package com.pkm.proyek.seismoalpha.laporan.umum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.laporan.LaporanActivity;
import com.pkm.proyek.seismoalpha.maps.PickLocation;
import com.pkm.proyek.seismoalpha.pelapor.Pelapor;
import com.pkm.proyek.seismoalpha.util.loadFromAPI;
import com.pkm.seismosense.backend.laporanUmumApi.model.LaporanUmum;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputUmumActivity extends AppCompatActivity {

    public static LaporanUmum laporanSave;

    private EditText inputKorbanJiwa;
    private EditText inputLukaBerat;
    private EditText inputLukaRingan;
    private EditText inputRusakBerat;
    private EditText inputRusakRingan;

    private String korbanJiwa;
    private String lukaBerat;
    private String lukaRingan;
    private String rusakBerat;
    private String rusakRingan;
    private Double longitude, latitude;

    private Bitmap foto;
    //String lokasiLaporan;
    //private LocationManager locationManager;
    private static Activity activity;
    public static ProgressBar loading;
    FloatingActionButton fab;


    Button upload;
    ImageView mImageView;
    private int widthScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_laporan);

        activity=this;
        //Loading init
        loading = (ProgressBar) findViewById(R.id.loading);
        loadStop();

        //Gempa apa ini?
        //Bundle bundle = getIntent().getExtras();
        //indexGempa = bundle.getInt("gempaid");

        //Init View
        inputKorbanJiwa = (EditText) findViewById(R.id.input_korban_jiwa);
        inputLukaBerat = (EditText) findViewById(R.id.input_luka_berat);
        inputLukaRingan = (EditText) findViewById(R.id.input_luka_ringan);

        inputRusakBerat = (EditText) findViewById(R.id.input_rusak_berat);
        inputRusakRingan = (EditText) findViewById(R.id.input_rusak_ringan);


        mImageView=(ImageView)findViewById(R.id.foto_upload);
        upload=(Button)findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStart();
                ambilDataInput();
                saveLaporan();
            }
        });

        try {
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void saveLaporan() {

        //Create Laporan Entity, it will be saved in Google Datastore
        laporanSave = new LaporanUmum();
        laporanSave.setId(Calendar.getInstance().getTimeInMillis());
        laporanSave.setGempaId(String.valueOf(LaporanActivity.indexGempa));
        laporanSave.setUsernamePelapor(Pelapor.akunIni.getUsername());
        laporanSave.setLokasiLat(PickLocation.position.latitude);   //LOKASI BASED ON LOCATION PICKER
        laporanSave.setLokasiLng(PickLocation.position.longitude);  //LOKASI BASED ON LOCATION PICKER
        if (PickLocation.positionAddress.contains("Gagal")){
            laporanSave.setAlamat(Pelapor.akunIni.getAlamat());
        }else {
            laporanSave.setAlamat(PickLocation.positionAddress);
        }
        laporanSave.setJumlahKorban(Integer.parseInt(korbanJiwa));
        laporanSave.setLukaBerat(Integer.parseInt(lukaBerat));
        laporanSave.setLukaRingan(Integer.parseInt(lukaRingan));
        laporanSave.setRusakBerat(Integer.parseInt(rusakBerat));
        laporanSave.setRusakRingan(Integer.parseInt(rusakRingan));

        //Handle foto saving
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte [] bytestream=bos.toByteArray();

        Toast.makeText(getApplicationContext(),"File Size :"+bytestream.length,Toast.LENGTH_SHORT).show();
        Log.d("FOTO SIZE", String.valueOf(bytestream.length));
        laporanSave.setFoto(android.util.Base64.encodeToString(bytestream, android.util.Base64.DEFAULT));

        //User Info
        laporanSave.setUrlFoto(Pelapor.akunIni.getUrlFoto());
        laporanSave.setNama(Pelapor.akunIni.getNama());

        //Store Now
        loadFromAPI.sync_mode = loadFromAPI.SYNC_MODE_POST_LAPORAN_UMUM;
        new loadFromAPI().execute(
                new Pair<Context, String>(this, String.valueOf(LaporanActivity.indexGempa))
        );
    }


    private void ambilDataInput() {
        //Get Location Pelapor
        latitude=Pelapor.akunIni.getLokasi().latitude;
        longitude=Pelapor.akunIni.getLokasi().longitude;

        //Get the text and prevent null
        korbanJiwa = inputKorbanJiwa.getText().toString();
        if (korbanJiwa.isEmpty()) {
            korbanJiwa = "0";
        }
        lukaBerat = inputLukaBerat.getText().toString();
        if (lukaBerat.isEmpty()) {
            lukaBerat = "0";
        }
        lukaRingan = inputLukaRingan.getText().toString();
        if (lukaRingan.isEmpty()) {
            lukaRingan = "0";
        }
        rusakBerat = inputRusakBerat.getText().toString();
        if (rusakBerat.isEmpty()) {
            rusakBerat = "0";
        }
        rusakRingan = inputRusakRingan.getText().toString();
        if (rusakRingan.isEmpty()) {
            rusakRingan = "0";
        }
    }

    public static void loadStart() {
        loading.setVisibility(View.VISIBLE);
    }

    public static void loadStop() {
        loading.setVisibility(View.GONE);
    }

    public static void failed(Context context) {
        Toast.makeText(context, "Laporan Tidak Terkirim", Toast.LENGTH_SHORT).show();
    }

    public static void success(Context context) {
        Toast.makeText(context, "Laporan Berhasil Terkirim", Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(activity);
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d("PHOTO","SAVE");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("PHOTO", String.valueOf(resultCode));
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            setDensityValue();
            setPic();
        }else {
            Log.d("PHOTO", "GAGAL");
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =image.getPath();
        return image;
    }

    private void setDensityValue() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthScreen=metrics.widthPixels;
    }

    private void setPic() {
        // Get the dimensions of the View

        Log.d("PHOTO","LOAD");
        int targetW = widthScreen; //mImageView.getWidth();
        int targetH = widthScreen;//mImageView.getHeight();
        Log.d("PHOTO", String.valueOf(targetH)+" "+targetW);
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        Log.d("PHOTO","LOAD");
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Log.d("PHOTO","LOAD");

        foto = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(foto);
    }
}
