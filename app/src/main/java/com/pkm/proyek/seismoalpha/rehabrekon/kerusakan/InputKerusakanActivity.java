package com.pkm.proyek.seismoalpha.rehabrekon.kerusakan;

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

import com.pkm.proyek.seismoalpha.laporan.tim.LaporanActivity;
import com.pkm.proyek.seismoalpha.pelapor.Pelapor;
import com.pkm.proyek.seismoalpha.maps.PickLocation;
import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.loadFromAPI;
import com.pkm.proyek.seismoalpha.rehabrekon.RehabRekonActivity;
import com.pkm.seismosense.backend.kerusakanLainApi.model.KerusakanLain;

import java.util.ArrayList;
import java.util.Calendar;

public class InputKerusakanActivity extends AppCompatActivity {

    public static ArrayList<Kerusakan> kerusakanArrayList;
    public static InputKerusakanLain kerusakanLain;
    public static ArrayList<com.pkm.seismosense.backend.kerusakanApi.model.Kerusakan> kerusakansBackEnd;
    public static KerusakanLain kerusakanLainBackEnd;

    public static int pageSelected;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private String [] category=new String[]{
            "Rumah",
            "Sekolah",
            "Kantor",
            "Sarana Kesehatan",
            "Tempat Ibadah",
            "Lain-lain"
    };
    private View.OnClickListener fabClickNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_korban);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pageSelected=0;

        kerusakanLain=new InputKerusakanLain();

        Log.d("KERUSAKAN", "CREATE KORBAN CATG");
        createKerusakansByCategory();

        fab=(FloatingActionButton)findViewById(R.id.fab);
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        fabClickNext=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewPager.arrowScroll(pageSelected+1);
                viewPager.setCurrentItem(pageSelected+1);
            }
        };
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //    Log.d("POOO",position+"--"+positionOffset+"--"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("PAGESELECTED", String.valueOf(position));
                Log.d("TOTAL KERUSAKAN", String.valueOf(kerusakanArrayList.size()));
                toolbar.setTitle(category[position]);
                if (position==category.length-1){
                    fab.setImageResource(R.drawable.ic_done_white_36dp);
                    fabDefault();
                }else {
                    fab.setOnClickListener(fabClickNext);
                    fab.setImageResource(R.mipmap.ic_keyboard_arrow_right_white);
                }

                //kerusakanArrayList.get(pageSelected).getFragment().getInputData();
                pageSelected=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //fabDefault();
        fab.setOnClickListener(fabClickNext);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    public void fabDefault(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TOTAL KERUSAKAN", String.valueOf(kerusakanArrayList.size()));
                //LAKUKAN SAVE DATA UNTUK SEMUA-1, ada lain2 soalnya
                for(int i = 0; i< kerusakanArrayList.size()-1; i++){
                    kerusakanArrayList.get(i).getFragment().getInputData();
                }

                //CREATE KORBAN FOR BACKEND
                kerusakanForBackEnd();

                //Untuk lain2
                kerusakanLainForBackEnd();

                //SAVE TO DATASTORE
                loadFromAPI.sync_mode=loadFromAPI.SYNC_MODE_POST_KERUSAKAN_RR;
                new loadFromAPI().execute(
                        new Pair<Context, String>(getApplicationContext(),"NOTHING")
                );


                RehabRekonActivity.kerusakan_checked=true;
                Log.d("TOTAL KERUSAKAN", String.valueOf(kerusakanArrayList.size()));
                startActivity(new Intent(getApplicationContext(), RehabRekonActivity.class));
            }
        });
    }

    private void kerusakanLainForBackEnd() {
        kerusakanLainBackEnd= new KerusakanLain();
        kerusakanLainBackEnd.setId(Calendar.getInstance().getTimeInMillis()+1);
        kerusakanLainBackEnd.setIdGempa(Long.parseLong(String.valueOf(LaporanActivity.indexGempa)));
        kerusakanLainBackEnd.setKategori("Lain-lain");
        kerusakanLainBackEnd.setUsername(Pelapor.akunIni.getUsername());
        kerusakanLainBackEnd.setLokasiLat(PickLocation.position.latitude);
        kerusakanLainBackEnd.setLokasiLng(PickLocation.position.longitude);
        kerusakanLainBackEnd.setAlamat(PickLocation.positionAddress);

        kerusakanLainBackEnd.setJmlRusakKios(kerusakanLain.jmlRusakKios);
        kerusakanLainBackEnd.setJmlRusakBangunan(kerusakanLain.jmlRusakBangunan);
        kerusakanLainBackEnd.setJmlRusakJembatan(kerusakanLain.jmlRusakJembatan);
        kerusakanLainBackEnd.setJmlRusakJalan(kerusakanLain.jmlRusakJalan);
        kerusakanLainBackEnd.setJmlRusakSawah(kerusakanLain.jmlRusakSawah);
        kerusakanLainBackEnd.setJmlRusakTebing(kerusakanLain.jmlRusakTebing);
        kerusakanLainBackEnd.setJmlRusakTalud(kerusakanLain.jmlRusakTalud);
        kerusakanLainBackEnd.setJmlRusakKebun(kerusakanLain.jmlRusakKebun);
        kerusakanLainBackEnd.setJmlRusakKolam(kerusakanLain.jmlRusakKolam);
        kerusakanLainBackEnd.setJmlRusakIrigasi(kerusakanLain.jmlRusakIrigasi);

    }

    private void kerusakanForBackEnd() {
        kerusakansBackEnd =new ArrayList<>();
        for(int i = 0; i< kerusakanArrayList.size()-1; i++){
            com.pkm.seismosense.backend.kerusakanApi.model.Kerusakan kerusakan=new com.pkm.seismosense.backend.kerusakanApi.model.Kerusakan();
            kerusakan.setId(Calendar.getInstance().getTimeInMillis()+i);
            kerusakan.setIdGempa(Long.parseLong(String.valueOf(LaporanActivity.indexGempa)));
            kerusakan.setKategori(kerusakanArrayList.get(i).getKategori());
            kerusakan.setUsername(Pelapor.akunIni.getUsername());
            kerusakan.setLokasiLat(PickLocation.position.latitude);
            kerusakan.setLokasiLng(PickLocation.position.longitude);
            kerusakan.setAlamat(PickLocation.positionAddress);

            kerusakan.setRusakBerat(kerusakanArrayList.get(i).getRusak_berat());
            kerusakan.setRusakSedang(kerusakanArrayList.get(i).getRusak_sedang());
            kerusakan.setRusakRingan(kerusakanArrayList.get(i).getRusak_ringan());
            kerusakansBackEnd.add(kerusakan);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        InputKerusakanAdapter adapter = new InputKerusakanAdapter(getSupportFragmentManager());
        adapter.setKerusakans(kerusakanArrayList);
        viewPager.setAdapter(adapter);
    }

    private void createKerusakansByCategory(){
        kerusakanArrayList =new ArrayList<>();
        for (int i=0;i<category.length;i++){
            InputKerusakan fragment=new InputKerusakan();
            fragment.setIndex(i);
            kerusakanArrayList.add(new Kerusakan(category[i],fragment));
        }
    }
}