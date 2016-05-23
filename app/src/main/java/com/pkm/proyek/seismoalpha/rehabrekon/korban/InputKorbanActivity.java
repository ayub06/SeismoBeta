package com.pkm.proyek.seismoalpha.rehabrekon.korban;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.pkm.proyek.seismoalpha.LaporanActivity;
import com.pkm.proyek.seismoalpha.Pelapor;
import com.pkm.proyek.seismoalpha.PickLocation;
import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.loadFromAPI;
import com.pkm.proyek.seismoalpha.rehabrekon.RehabRekonActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class InputKorbanActivity extends AppCompatActivity {

    public static ArrayList<Korban> korbanArrayList;
    public static ArrayList<com.pkm.seismosense.backend.korbanApi.model.Korban> korbansBackEnd;
    public static int pageSelected;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private String [] category=new String[]{
            "Meninggal",
            "Hilang",
            "Luka-luka",
            "Menderita",
            "Mengungsi"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_korban);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pageSelected=0;
        createKorbansByCategory();

        fab=(FloatingActionButton)findViewById(R.id.fab);
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //    Log.d("POOO",position+"--"+positionOffset+"--"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("PAGESELECTED", String.valueOf(position));
                toolbar.setTitle(category[position]);
                if (position==category.length-1){
                    fab.setImageResource(R.drawable.building);
                }

                korbanArrayList.get(pageSelected).getFragment().getInputData();
                pageSelected=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fabDefault();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    public void fabDefault(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //LAKUKAN SAVE DATA UNTUK SEMUA
                for(int i=0;i<korbanArrayList.size();i++){
                    korbanArrayList.get(i).getFragment().getInputData();
                }

                //CREATE KORBAN FOR BACKEND
                korbanForBackEnd();

                //SAVE TO DATASTORE
                loadFromAPI.sync_mode=loadFromAPI.SYNC_MODE_POST_KORBAN_RR;
                new loadFromAPI().execute(
                        new Pair<Context, String>(getApplicationContext(),"NOTHING")
                );
                RehabRekonActivity.korban_checked=true;

                startActivity(new Intent(getApplicationContext(), RehabRekonActivity.class));
            }
        });
    }

    private void korbanForBackEnd() {
        korbansBackEnd=new ArrayList<>();
        for(int i=0;i<korbanArrayList.size();i++){
            com.pkm.seismosense.backend.korbanApi.model.Korban korban=new com.pkm.seismosense.backend.korbanApi.model.Korban();
            korban.setId(Calendar.getInstance().getTimeInMillis()+i);
            korban.setIdGempa(Long.parseLong(String.valueOf(LaporanActivity.indexGempa)));
            korban.setKategori(korbanArrayList.get(i).getKategori());
            korban.setUsername(Pelapor.akunIni.getUsername());
            korban.setLokasiLat(PickLocation.position.latitude);
            korban.setLokasiLng(PickLocation.position.longitude);
            korban.setAlamat(PickLocation.positionAddress);

            korban.setAnak(korbanArrayList.get(i).getAnak());
            korban.setDewasa(korbanArrayList.get(i).getDewasa());
            korban.setLansia(korbanArrayList.get(i).getLansia());

            korban.setAnakp(korbanArrayList.get(i).getAnakp());
            korban.setDewasap(korbanArrayList.get(i).getDewasap());
            korban.setLansiap(korbanArrayList.get(i).getLansiap());
            korban.setHamil(korbanArrayList.get(i).getHamil());

            korbansBackEnd.add(korban);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        InputKorbanAdapter adapter = new InputKorbanAdapter(getSupportFragmentManager());
        adapter.setKorbans(korbanArrayList);
        viewPager.setAdapter(adapter);
    }

    private void createKorbansByCategory(){
        korbanArrayList=new ArrayList<>();
        for (int i=0;i<category.length;i++){
            InputKorban fragment=new InputKorban();
            fragment.setIndex(i);
            korbanArrayList.add(new Korban(category[i],fragment));
        }
    }
}