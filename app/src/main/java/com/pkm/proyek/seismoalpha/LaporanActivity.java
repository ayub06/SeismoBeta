package com.pkm.proyek.seismoalpha;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

public class LaporanActivity extends AppCompatActivity {

    public static Activity activity;
    public static int indexGempa;
    public static RecyclerView rv;
    public static ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        loading = (ProgressBar) findViewById(R.id.loading);
        loadStart();

        activity=this;

        //Gempa apa ini?

        final Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            indexGempa=bundle.getInt("gempaid");
        }

        //Create Adapter
        rv = (RecyclerView)findViewById(R.id.rv_laporan);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        //Anticipate NULL
        Laporan.laporanArrayList=new ArrayList<>();

        //Get Laporan from Server
        loadFromAPI.from=loadFromAPI.LAPORAN_ACTIVITY;
        loadFromAPI.sync_mode = loadFromAPI.SYNC_MODE_GET_LAPORAN;
        new loadFromAPI().execute(
                new Pair<Context, String>(this, String.valueOf(indexGempa))
        );


        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        if(LoginActivity.umum||
                !(Pelapor.akunIni.getAlamat().contains(Gempa.gempaArrayList.get(indexGempa).getNama()))){
            assert fab != null;
            fab.setVisibility(View.GONE);
        }
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPickLocation(false);
            }
        });

        try {
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void showLaporanlist(){
        /*ArrayList <Laporan> laporans=new ArrayList<>();
        for (int i=0;i<Laporan.laporanArrayList.size();i++){
            if (Laporan.laporanArrayList.get())
        }*/
        //Assign to Adapter
        LaporanAdapter adapter = new LaporanAdapter(Laporan.laporanArrayList,activity);
        rv.setAdapter(adapter);
        loadStop();
    }
    public static void loadStart() {
        loading.setVisibility(View.VISIBLE);
    }

    public static void loadStop() {
        loading.setVisibility(View.GONE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rehab_rekon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rehab_rek:
                goToPickLocation(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToPickLocation(boolean fromRehab){
        Bundle bundle=new Bundle();
        bundle.putBoolean("fromRehab",fromRehab);
        Intent intent=new Intent(getApplicationContext(),PickLocation.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}