package com.pkm.proyek.seismoalpha.laporan.tim;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.laporan.LaporanActivity;
import com.pkm.proyek.seismoalpha.util.loadFromAPI;
import com.pkm.proyek.seismoalpha.maps.PickLocation;
import com.pkm.proyek.seismoalpha.pelapor.Pelapor;
import com.pkm.seismosense.backend.laporanApi.model.Laporan;
public class InputActivity extends AppCompatActivity {

    public static com.pkm.seismosense.backend.laporanApi.model.Laporan laporanSave;

    EditText inputKorbanJiwa;
    EditText inputLukaBerat;
    EditText inputLukaRingan;
    EditText inputRusakBerat;
    EditText inputRusakRingan;


    String korbanJiwa;
    String lukaBerat;
    String lukaRingan;
    String rusakBerat;
    String rusakRingan;
    Double longitude, latitude;
    //String lokasiLaporan;
    //private LocationManager locationManager;
    private static Activity activity;
    public static ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        laporanSave = new Laporan();
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

        //Store Now
        loadFromAPI.sync_mode = loadFromAPI.SYNC_MODE_POST_LAPORAN;
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



}
