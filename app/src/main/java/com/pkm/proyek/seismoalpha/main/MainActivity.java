package com.pkm.proyek.seismoalpha.main;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.pkm.proyek.seismoalpha.util.ImageRound;
import com.pkm.proyek.seismoalpha.pelapor.LoginActivity;
import com.pkm.proyek.seismoalpha.pelapor.Pelapor;
import com.pkm.proyek.seismoalpha.R;

import java.util.ArrayList;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {

    private static RelativeLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading=(RelativeLayout) findViewById(R.id.loading);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_main);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        //Pelapor dan Gempa hanya bisa dibuat oleh admin
        //Sehingga disini dibuat default dulu
        loadDefaultGempa();
        loadDefaultPelapor();

        //Akun ini?
        if(!LoginActivity.umum) {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null){
                setAkunIni(bundle.getString("username"));
            }
        }

        GempaAdapter adapter = new GempaAdapter(Gempa.gempaArrayList,this);
        rv.setAdapter(adapter);
    }

    private void setAkunIni(String username) {
        for(int i = 0; i< Pelapor.pelaporArrayList.size(); i++){
            if(Pelapor.pelaporArrayList.get(i).getUsername().equals(username)){
                Pelapor.akunIni=Pelapor.pelaporArrayList.get(i);
                Log.d("PELAPOR AKUN INI","OK");
            }
        }
    }

    public void loadDefaultPelapor(){
        Pelapor.pelaporArrayList=new ArrayList<>();

        Pelapor.pelaporArrayList.add(new Pelapor(
                0,
                ImageRound.getRoundedCornerBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.fransiskus_a_kristiawan)),
                "Frans",
                "franspkm",
                "Fransiskus A Kristiawan",
                "Jl. Buntu, Terban,Gondokusuman, Kota Yogyakarta, Daerah Istimewa Yogyakarta",
                new LatLng(-7.778664, 110.372719)));
        Pelapor.pelaporArrayList.add(new Pelapor(
                1,
                ImageRound.getRoundedCornerBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.rais_afif)),
                "Rais",
                "raispkm",
                "Rais Afif",
                "Jalan KRT Judodiningrat,Siraman,Wonosari,Gunung Kidul,Yogyakarta",
                new LatLng(-7.977934, 110.601766)));
        Pelapor.pelaporArrayList.add(new Pelapor(
                2,
                ImageRound.getRoundedCornerBitmap(
                                BitmapFactory.decodeResource(getResources(), R.drawable.brigita_petra)),
                "Brigita",
                "brigitapkm",
                "Brigita Petra",
                "Jalan Tandi, Ateuk Jawo, Baiturrahman, Kota Banda Aceh, Aceh",
                new LatLng(5.534754, 95.322365)));
        Pelapor.pelaporArrayList.add(new Pelapor(
                3,
                ImageRound.getRoundedCornerBitmap(
                                BitmapFactory.decodeResource(getResources(), R.drawable.putri_azizah)),
                "Putri",
                "putripkm",
                "Putri Azizah",
                "Kajhu,Baitussalam,Kabupaten Aceh Besar, Aceh",
                new LatLng(5.595154, 95.385584)));
        Pelapor.pelaporArrayList.add(new Pelapor(
                4,
                ImageRound.getRoundedCornerBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.tegar_pualam)),
                "Tegar",
                "tegarpkm",
                "Tegar Pualam",
                "Mon Ikeun, Kecamatan Lhoknga, Kabupaten Aceh Besar, Aceh",
                new LatLng(5.478599, 95.238316)));
    }

    public void loadDefaultGempa(){
        Gempa.gempaArrayList=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        calendar.set(2006,5,24);
        Gempa.gempaArrayList.add(new Gempa(
                0,
                "Yogyakarta",
                new LatLng(-8.607487, 110.275697),
                calendar,
                5.5,
                9, 3, 3,
                3, 3
        ));

        Calendar calendarr=Calendar.getInstance();
        calendarr.set(2004, 12, 26);
        Gempa.gempaArrayList.add(new Gempa(
                1,
                "Aceh",
                new LatLng(5.671459, 94.910895),
                calendarr,
                5.5,
                9, 3, 3,
                3, 3
        ));
    }

    public static void startLoading(){
        loading.setVisibility(View.VISIBLE);
    }

    public static void stopLoading(){
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}