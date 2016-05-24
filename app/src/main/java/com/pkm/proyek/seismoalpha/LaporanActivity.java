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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

public class LaporanActivity extends AppCompatActivity {

    public static Activity activity;
    public static int indexGempa;
    public static RecyclerView rv;
    public static ProgressBar loading;
    private boolean fabHide = true;
    private View layerPutih;
    private TextView textFabLaporan;
    private TextView textFabRehabRekon;
    private FloatingActionButton rehabRekonFab;
    private FloatingActionButton fab;
    private FloatingActionButton showFab;
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

        /*showFab = (FloatingActionButton)findViewById(R.id.fab_show);
        if(LoginActivity.umum||
                !(Pelapor.akunIni.getAlamat().contains(Gempa.gempaArrayList.get(indexGempa).getNama()))){
            assert showFab != null;
            showFab.setVisibility(View.GONE);
        }*/

        //menthod untuk menghandle onClick dan fab agar tidak ruwet disini
        handleSetOnClick();
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


/*

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
*/

    private void goToPickLocation(boolean fromRehab){
        Bundle bundle=new Bundle();
        bundle.putBoolean("fromRehab",fromRehab);
        Intent intent=new Intent(getApplicationContext(),PickLocation.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void showFloatingButton() {
        layerPutih.setVisibility(View.VISIBLE);
        textFabLaporan.setVisibility(View.VISIBLE);
        textFabRehabRekon.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        rehabRekonFab.setVisibility(View.VISIBLE);
        showFab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_close_white));
        fabHide = false;
    }

    private  void hideFloatingButton() {
        layerPutih.setVisibility(View.GONE);
        textFabLaporan.setVisibility(View.GONE);
        textFabRehabRekon.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
        rehabRekonFab.setVisibility(View.GONE);
        showFab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_add_white));
        fabHide = true;
    }

    private void handleSetOnClick() {

        layerPutih = findViewById(R.id.view_white_opacity);
        textFabLaporan = (TextView)findViewById(R.id.text_fab_laporan);
        textFabRehabRekon = (TextView)findViewById(R.id.text_fab_rehab_rekon);
        rehabRekonFab = (FloatingActionButton)findViewById(R.id.fab_rehab_rekon);
        showFab = (FloatingActionButton)findViewById(R.id.fab_show);
        //menampilkan dan menghilangkan submenu fab
        assert showFab != null;
        showFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabHide) {
                    showFloatingButton();

                    //hide fab untuk rehab rekon jika user umum
                    if(LoginActivity.umum||
                            !(Pelapor.akunIni.getAlamat().contains(Gempa.gempaArrayList.get(indexGempa).getNama()))){
                        textFabRehabRekon.setVisibility(View.GONE);
                        rehabRekonFab.setVisibility(View.GONE);
                    }

                } else {
                    hideFloatingButton();
                }

            }
        });

        layerPutih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFloatingButton();
            }
        });
        //fab rehab rekon handling
        assert rehabRekonFab != null;
        rehabRekonFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPickLocation(true);
            }
        });

        fab=(FloatingActionButton)findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginActivity.umum||
                        !(Pelapor.akunIni.getAlamat().contains(Gempa.gempaArrayList.get(indexGempa).getNama()))){
                    startActivity(new Intent(getApplicationContext(),InputUmum.class));
                    textFabRehabRekon.setVisibility(View.GONE);
                    rehabRekonFab.setVisibility(View.GONE);
                } else {
                    goToPickLocation(false);
                }
            }
        });

        textFabLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPickLocation(false);
            }
        });

        textFabRehabRekon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPickLocation(true);
            }
        });

    }
}