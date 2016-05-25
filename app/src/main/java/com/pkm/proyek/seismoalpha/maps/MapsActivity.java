package com.pkm.proyek.seismoalpha.maps;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.laporan.tim.Laporan;
import com.pkm.proyek.seismoalpha.main.Gempa;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback {

    public static final String MODE="mode";
    public static final String ID_GEMPA_OR_LAPORAN="id";
    public static final int DISPLAY_GEMPA=0;
    public static final int DISPLAY_LAPORAN=1;

    private int mode;
    private int id;
    private GoogleMap mMap;
    private ClusterManager<MyItem> mClusterManager;
    private ClusterManager<MyItem> mNullClusterManager;

    private TabLayout tabLayout;
    private ArrayList<LatLng> list;
    private Marker pusat;

    private Spinner spinnerKiri, spinnerKanan;
    private int spinnerKiriStatus = 0, spinnerKananStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        spinnerKiri = (Spinner)findViewById(R.id.umum_tim_spinner);
        spinnerKanan = (Spinner)findViewById(R.id.filter_spinner);
        addListToSpinner();
        spinnerKiri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerKiriStatus = position;
                handleMultipleKondisi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerKanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerKananStatus = position;
                handleMultipleKondisi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Mode apa?
        Bundle bundle=getIntent().getExtras();
        mode=bundle.getInt(MODE);
        id = bundle.getInt(ID_GEMPA_OR_LAPORAN);

        if (mode!=DISPLAY_LAPORAN) {
            //SET TAB LAYOUT
            setTabLayout();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void handleMultipleKondisi() {
        if((spinnerKiriStatus == 0) && spinnerKananStatus == 0){
            ambilSemuaData();
        } else if ((spinnerKiriStatus == 0) && spinnerKananStatus == 1) {
            ambilSemuaData();
            ambilDataMeninggal();
        } else if ((spinnerKiriStatus == 0) && spinnerKananStatus == 2) {
            ambilSemuaData();
            ambilDataLukaBerat();
        } else if ((spinnerKiriStatus == 0) && spinnerKananStatus == 3) {
            ambilSemuaData();
            ambilDataLukaRingan();
        } else if ((spinnerKiriStatus == 0) && spinnerKananStatus == 4) {
            ambilSemuaData();
            ambilDataRusakBerat();
        } else if ((spinnerKiriStatus == 0) && spinnerKananStatus == 5) {
            ambilSemuaData();
            ambilDataRusakRingan();
        } else if ((spinnerKiriStatus == 1) && spinnerKananStatus == 0) {
            ambilDataTim();
        } else if ((spinnerKiriStatus == 1) && spinnerKananStatus == 1) {
            ambilDataTim();
            ambilDataMeninggal();
        } else if ((spinnerKiriStatus == 1) && spinnerKananStatus == 2) {
            ambilDataTim();
            ambilDataLukaBerat();
        } else if ((spinnerKiriStatus == 1) && spinnerKananStatus == 3) {
            ambilDataTim();
            ambilDataLukaRingan();
        } else if ((spinnerKiriStatus == 1) && spinnerKananStatus == 4) {
            ambilDataTim();
            ambilDataRusakBerat();
        } else if ((spinnerKiriStatus == 1) && spinnerKananStatus == 5) {
            ambilDataTim();
            ambilDataRusakRingan();
        } else if ((spinnerKiriStatus == 2) && spinnerKananStatus == 0) {
            ambilDataUmum();
        } else if ((spinnerKiriStatus == 2) && spinnerKananStatus == 1) {
            ambilDataUmum();
            ambilDataMeninggal();
        } else if ((spinnerKiriStatus == 2) && spinnerKananStatus == 2) {
            ambilDataUmum();
            ambilDataLukaBerat();
        } else if ((spinnerKiriStatus == 2) && spinnerKananStatus == 3) {
            ambilDataUmum();
            ambilDataLukaRingan();
        } else if ((spinnerKiriStatus == 2) && spinnerKananStatus == 4) {
            ambilDataUmum();
            ambilDataRusakBerat();
        } else if ((spinnerKiriStatus == 2) && spinnerKananStatus == 5) {
            ambilDataUmum();
            ambilDataRusakRingan();
        }
    }

    private void addListToSpinner() {
        List<String> listKiri = new ArrayList<String>();
        List<String> listKanan = new ArrayList<String>();
        listKiri.add("Semua");
        listKiri.add("Tim");
        listKiri.add("Umum");
        listKanan.add("Semua");
        listKanan.add("Korban Jiwa");
        listKanan.add("Luka Berat");
        listKanan.add("Luka Ringan");
        listKanan.add("Rusak Berat");
        listKanan.add("Rusak Ringan");
        ArrayAdapter<String> adapterKiri = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listKiri);
        adapterKiri.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKiri.setAdapter(adapterKiri);
        ArrayAdapter<String> adapterKanan = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listKanan);
        adapterKanan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKanan.setAdapter(adapterKanan);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mode==DISPLAY_LAPORAN){
            /**
             * ----MODE LAPORAN----
             * Marker ditujukan pada : laporan yang dipilih (default camera), laporan2 lain
             * Default marker show : laporan terpilih
             * Sehingga perlu ArrayList berisi semua laporan dari gempa ID
             * Marker diclick keluar window dengan layout item_report
             * Binding data dari data di arraylist ke item_report
             */

            tabLayout.setVisibility(View.GONE);

            //Show all LAPORAN MARKER
            showAllLaporanMarker();

            //Camero focus to Selected Laporan and show the window detail
            focusOnMarker(Laporan.laporanArrayList.get(id).getLokasi());

        }else{

            /**
             * ----MODE GEMPA----
             * Marker ditujukan pada : pusat gempa (default camera), laporan2 digempa tersebut
             * Default Marker show : pusat Gempa
             * Sehingga perlu ArrayList berisi semua laporan dari gempa ID
             * Marker diclick keluar window dengan layout item_report
             * Binding data dari data di arraylist ke item_report
             */

            //Init Cluster
            mClusterManager = new ClusterManager<MyItem>(this, mMap);
            mMap.setOnCameraChangeListener(mClusterManager);
            mMap.setOnMarkerClickListener(mClusterManager);

            //Here Use Read MyItem to Cluster Marker
            readLaporanForClustering();


            //Add Marker for Pusat Gempa
            pusat=mMap.addMarker(new MarkerOptions()
                            .position(Gempa.gempaArrayList.get(id).getPusat())
                            .title("pusat")
            );

            //Camero focus to Pusat Gemap and show the window detail
            focusOnMarker(Gempa.gempaArrayList.get(id).getPusat());
        }

        //Setting all window that show if clicked in the marker
        setWindowInMarker();
    }

    private void setTabLayout() {

        TabLayout.Tab tab=tabLayout.newTab();
        tab.setText("Laporan");
        TabLayout.Tab tabs=tabLayout.newTab();
        tabs.setText("Heatmap");
        tabLayout.addTab(tab);
        tabLayout.addTab(tabs);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Laporan")){
                //    Toast.makeText(getApplicationContext(),"LAPORAN",Toast.LENGTH_SHORT).show();
                    clearHeatMap();
                    showCluster();
                }else {
                //    Toast.makeText(getApplicationContext(),"HEATMAP",Toast.LENGTH_SHORT).show();
                    clearCluster();
                    showHeatMap();
                    if(!list.isEmpty()) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), 9));
                    }else {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Gempa.gempaArrayList.get(id).getPusat(), 7));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void readLaporanForClustering() {
        list=new ArrayList<>();
        try {
            for(int i = 0; i < Laporan.laporanArrayList.size(); i++) {
                MyItem myItem=new MyItem(Laporan.laporanArrayList.get(i).getLokasi().latitude,Laporan.laporanArrayList.get(i).getLokasi().longitude);
                myItem.setId(i);
                mClusterManager.addItem(myItem);
                list.add(Laporan.laporanArrayList.get(i).getLokasi());
            }

            if (Laporan.laporanArrayList.size()==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Laporan.laporanArrayList.get(0).getLokasi(), 5));
            }
        } catch (NullPointerException e){
            e.getStackTrace();
        }
    }

    private void setWindowInMarker() {

        if (mode==DISPLAY_LAPORAN) {
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    //Get index location in array from clicked Marker
                    String title = marker.getTitle();
                    if (title.equals("pusat")) {
                        return showWindowGempa();
                    } else {
                        //Laporan
                        int i = Integer.parseInt(title);
                        return showWindowLaporan(i);
                    }
                }

                private View showWindowGempa() {
                    TextView waktu;
                    TextView nama;
                    TextView sr;

                    TextView jumlah_korban;
                    TextView luka_berat;
                    TextView luka_ringan;

                    TextView rusak_berat;
                    TextView rusak_ringan;

                    Button korban;
                    Button peta;

                    View itemView = getLayoutInflater().inflate(R.layout.item_news_map, null);
                    nama = (TextView) itemView.findViewById(R.id.lokasi_gempa);
                    waktu = (TextView) itemView.findViewById(R.id.tanggal_gempa);
                    sr = (TextView) itemView.findViewById(R.id.skala_gempa);

                    jumlah_korban = (TextView) itemView.findViewById(R.id.total_korban_jiwa);
                    luka_berat = (TextView) itemView.findViewById(R.id.total_luka_berat);
                    luka_ringan = (TextView) itemView.findViewById(R.id.total_luka_ringan);

                    rusak_berat = (TextView) itemView.findViewById(R.id.total_rusak_berat);
                    rusak_ringan = (TextView) itemView.findViewById(R.id.total_rusak_ringan);

                    korban = (Button) itemView.findViewById(R.id.button_korban_timeline);
                    peta = (Button) itemView.findViewById(R.id.button_pemetaan);

                    waktu.setText(String.valueOf(
                            Gempa.gempaArrayList.get(id).getJam() + " | " + Gempa.gempaArrayList.get(id).getTanggalSingkat()));
                    nama.setText(Gempa.gempaArrayList.get(id).getNama());
                    sr.setText(String.valueOf(String.valueOf(Gempa.gempaArrayList.get(id).getSr())));
                    jumlah_korban.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_korban()));
                    luka_berat.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_luka_berat()));
                    luka_ringan.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_luka_ringan()));
                    rusak_ringan.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_rusak_ringan()));
                    rusak_berat.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_rusak_berat()));

                    korban.setVisibility(View.GONE);
                    peta.setVisibility(View.GONE);
                    return itemView;
                }

                private View showWindowLaporan(int i) {
                    TextView waktu;
                    ImageView foto;
                    TextView nama;
                    TextView alamat;

                    TextView jumlah_korban;
                    TextView luka_berat;
                    TextView luka_ringan;

                    TextView rusak_berat;
                    TextView rusak_ringan;

                    View itemView = getLayoutInflater().inflate(R.layout.item_report, null);
                    waktu = (TextView) itemView.findViewById(R.id.tanggal_lapor);
                    foto = (ImageView) itemView.findViewById(R.id.foto_pelapor);
                    nama = (TextView) itemView.findViewById(R.id.nama_pelapor);
                    alamat = (TextView) itemView.findViewById(R.id.lokasi_laporan);

                    jumlah_korban = (TextView) itemView.findViewById(R.id.lapor_korban_jiwa);
                    luka_berat = (TextView) itemView.findViewById(R.id.lapor_luka_berat);
                    luka_ringan = (TextView) itemView.findViewById(R.id.lapor_luka_ringan);

                    rusak_berat = (TextView) itemView.findViewById(R.id.lapor_rusak_berat);
                    rusak_ringan = (TextView) itemView.findViewById(R.id.lapor_rusak_ringan);

                    //Data Binding
                    waktu.setText(Laporan.laporanArrayList.get(i).getJam() + " | " + Laporan.laporanArrayList.get(i).getTanggalSingkat());
                    foto.setImageBitmap(Laporan.laporanArrayList.get(i).getPelapor().getFoto());
                    nama.setText(Laporan.laporanArrayList.get(i).getPelapor().getNama());
                    alamat.setText(Laporan.laporanArrayList.get(i).getAlamat());

                    jumlah_korban.setText(String.valueOf(Laporan.laporanArrayList.get(i).getJumlah_korban()));
                    luka_berat.setText(String.valueOf(Laporan.laporanArrayList.get(i).getLuka_berat()));
                    luka_ringan.setText(String.valueOf(Laporan.laporanArrayList.get(i).getLuka_ringan()));

                    rusak_ringan.setText(String.valueOf(Laporan.laporanArrayList.get(i).getRusak_ringan()));
                    rusak_berat.setText(String.valueOf(Laporan.laporanArrayList.get(i).getRusak_berat()));
                    return itemView;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    return null;
                }
            });
        }else {
            //SET WINDOW FOR GEMPA WITH CLUSTER
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {

                    //PUSAT GEMPA
                    if (marker.getTitle()!=null){
                        if (marker.getTitle().equals("pusat")) {
                            return showWindowGempa();
                        }
                    }

                    //LAPORAN
                    int idLaporan = 0;
                    //Getting ID Laporan
                    for (int i=0;i<Laporan.laporanArrayList.size();i++){
                        if (Laporan.laporanArrayList.get(i).getLokasi().latitude==marker.getPosition().latitude){
                            //Toast.makeText(getApplicationContext(),"FOUND "+i,Toast.LENGTH_SHORT).show();
                            idLaporan=i;
                        }
                    }
                    return showWindowLaporan(idLaporan);
                }

                private View showWindowLaporan(int i) {
                    TextView waktu;
                    ImageView foto;
                    TextView nama;
                    TextView alamat;

                    TextView jumlah_korban;
                    TextView luka_berat;
                    TextView luka_ringan;

                    TextView rusak_berat;
                    TextView rusak_ringan;

                    View itemView = getLayoutInflater().inflate(R.layout.item_report, null);
                    waktu = (TextView) itemView.findViewById(R.id.tanggal_lapor);
                    foto = (ImageView) itemView.findViewById(R.id.foto_pelapor);
                    nama = (TextView) itemView.findViewById(R.id.nama_pelapor);
                    alamat = (TextView) itemView.findViewById(R.id.lokasi_laporan);

                    jumlah_korban = (TextView) itemView.findViewById(R.id.lapor_korban_jiwa);
                    luka_berat = (TextView) itemView.findViewById(R.id.lapor_luka_berat);
                    luka_ringan = (TextView) itemView.findViewById(R.id.lapor_luka_ringan);

                    rusak_berat = (TextView) itemView.findViewById(R.id.lapor_rusak_berat);
                    rusak_ringan = (TextView) itemView.findViewById(R.id.lapor_rusak_ringan);

                    //Data Binding
                    waktu.setText(Laporan.laporanArrayList.get(i).getJam() + " | " + Laporan.laporanArrayList.get(i).getTanggalSingkat());
                    foto.setImageBitmap(Laporan.laporanArrayList.get(i).getPelapor().getFoto());
                    nama.setText(Laporan.laporanArrayList.get(i).getPelapor().getNama());
                    alamat.setText(Laporan.laporanArrayList.get(i).getAlamat());

                    jumlah_korban.setText(String.valueOf(Laporan.laporanArrayList.get(i).getJumlah_korban()));
                    luka_berat.setText(String.valueOf(Laporan.laporanArrayList.get(i).getLuka_berat()));
                    luka_ringan.setText(String.valueOf(Laporan.laporanArrayList.get(i).getLuka_ringan()));

                    rusak_ringan.setText(String.valueOf(Laporan.laporanArrayList.get(i).getRusak_ringan()));
                    rusak_berat.setText(String.valueOf(Laporan.laporanArrayList.get(i).getRusak_berat()));
                    return itemView;
                }

                private View showWindowGempa() {
                    TextView waktu;
                    TextView nama;
                    TextView sr;

                    TextView jumlah_korban;
                    TextView luka_berat;
                    TextView luka_ringan;

                    TextView rusak_berat;
                    TextView rusak_ringan;

                    Button korban;
                    Button peta;

                    View itemView = getLayoutInflater().inflate(R.layout.item_news_map, null);
                    nama = (TextView) itemView.findViewById(R.id.lokasi_gempa);
                    waktu = (TextView) itemView.findViewById(R.id.tanggal_gempa);
                    sr = (TextView) itemView.findViewById(R.id.skala_gempa);

                    jumlah_korban = (TextView) itemView.findViewById(R.id.total_korban_jiwa);
                    luka_berat = (TextView) itemView.findViewById(R.id.total_luka_berat);
                    luka_ringan = (TextView) itemView.findViewById(R.id.total_luka_ringan);

                    rusak_berat = (TextView) itemView.findViewById(R.id.total_rusak_berat);
                    rusak_ringan = (TextView) itemView.findViewById(R.id.total_rusak_ringan);

                    korban = (Button) itemView.findViewById(R.id.button_korban_timeline);
                    peta = (Button) itemView.findViewById(R.id.button_pemetaan);

                    waktu.setText(String.valueOf(
                            Gempa.gempaArrayList.get(id).getJam() + " | " + Gempa.gempaArrayList.get(id).getTanggalSingkat()));
                    nama.setText(Gempa.gempaArrayList.get(id).getNama());
                    sr.setText(String.valueOf(String.valueOf(Gempa.gempaArrayList.get(id).getSr())));
                    jumlah_korban.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_korban()));
                    luka_berat.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_luka_berat()));
                    luka_ringan.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_luka_ringan()));
                    rusak_ringan.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_rusak_ringan()));
                    rusak_berat.setText(String.valueOf(Gempa.gempaArrayList.get(id).getTotal_rusak_berat()));

                    korban.setVisibility(View.GONE);
                    peta.setVisibility(View.GONE);
                    return itemView;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    //View itemView = getLayoutInflater().inflate(R.layout.item_report, null);
                    return null;
                }
            });

            mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
                @Override
                public boolean onClusterClick(Cluster<MyItem> cluster) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), 10));
                    return false;
                }
            });
        }
    }

    private void focusOnMarker(LatLng lokasi) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 8));
    }

    private void showAllLaporanMarker() {
        for(int i=0;i<Laporan.laporanArrayList.size();i++){
            mMap.addMarker(new MarkerOptions()
                            .position(Laporan.laporanArrayList.get(i).getLokasi())
                            .title(String.valueOf(i))
            );
        }
    }

    private void clearCluster(){
        mClusterManager.clearItems();
    }

    private void showCluster(){
        onMapReady(mMap);
    }

    private void clearHeatMap(){
        mMap.clear();
    }

    private void showHeatMap(){
        pusat.setVisible(false);
        heatMap();
    }

    private void heatMap() {
        // Create a heat map tile provider, passing it the latlngs of the police stations.
        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    /*private ArrayList<Laporan> loadLaporanByIDGempa() {
        //Get Laporan from Server
        if(Laporan.laporanArrayList==null) {
            loadFromAPI.sync_mode = loadFromAPI.SYNC_MODE_GET_LAPORAN;
            new loadFromAPI().execute(
                    new Pair<Context, String>(this, "Load Laporan")
            );
        }
        return Laporan.laporanArrayList;

    }*/

    private void ambilSemuaData() {
        list=new ArrayList<>();
        try {
            for(int i = 0; i < Laporan.laporanArrayList.size(); i++) {
                MyItem myItem=new MyItem(Laporan.laporanArrayList.get(i).getLokasi().latitude,Laporan.laporanArrayList.get(i).getLokasi().longitude);
                myItem.setId(i);
                mClusterManager.addItem(myItem);
                list.add(Laporan.laporanArrayList.get(i).getLokasi());
            }

            if (Laporan.laporanArrayList.size()==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Laporan.laporanArrayList.get(0).getLokasi(), 5));
            }
        } catch (NullPointerException e){
            e.getStackTrace();
        }
    }

    private void ambilDataTim() {

    }

    private void ambilDataUmum() {

    }

    private void ambilDataMeninggal() {
        list=new ArrayList<>();
        try {
            for(int i = 0; i < Laporan.laporanArrayList.size(); i++) {
                if(Laporan.laporanArrayList.get(i).getJumlah_korban() == 0) {
                    i++;
                } else {
                    MyItem myItem = new MyItem(Laporan.laporanArrayList.get(i).getLokasi().latitude, Laporan.laporanArrayList.get(i).getLokasi().longitude);
                    myItem.setId(i);
                    mClusterManager.addItem(myItem);
                    list.add(Laporan.laporanArrayList.get(i).getLokasi());
                }
            }

            if (Laporan.laporanArrayList.size()==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Laporan.laporanArrayList.get(0).getLokasi(), 5));
            }
        } catch (NullPointerException e){
            e.getStackTrace();
        }
    }

    private void ambilDataLukaBerat() {
        list=new ArrayList<>();
        try {
            for(int i = 0; i < Laporan.laporanArrayList.size(); i++) {
                if(Laporan.laporanArrayList.get(i).getLuka_berat() == 0) {
                    i++;
                } else {
                    MyItem myItem = new MyItem(Laporan.laporanArrayList.get(i).getLokasi().latitude, Laporan.laporanArrayList.get(i).getLokasi().longitude);
                    myItem.setId(i);
                    mClusterManager.addItem(myItem);
                    list.add(Laporan.laporanArrayList.get(i).getLokasi());
                }
            }

            if (Laporan.laporanArrayList.size()==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Laporan.laporanArrayList.get(0).getLokasi(), 5));
            }
        } catch (NullPointerException e){
            e.getStackTrace();
        }
    }

    private void ambilDataLukaRingan() {
        list=new ArrayList<>();
        try {
            for(int i = 0; i < Laporan.laporanArrayList.size(); i++) {
                if(Laporan.laporanArrayList.get(i).getLuka_ringan() == 0) {
                    i++;
                } else {
                    MyItem myItem = new MyItem(Laporan.laporanArrayList.get(i).getLokasi().latitude, Laporan.laporanArrayList.get(i).getLokasi().longitude);
                    myItem.setId(i);
                    mClusterManager.addItem(myItem);
                    list.add(Laporan.laporanArrayList.get(i).getLokasi());
                }
            }

            if (Laporan.laporanArrayList.size()==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Laporan.laporanArrayList.get(0).getLokasi(), 5));
            }
        } catch (NullPointerException e){
            e.getStackTrace();
        }
    }

    private void ambilDataRusakBerat() {
        list=new ArrayList<>();
        try {
            for(int i = 0; i < Laporan.laporanArrayList.size(); i++) {
                if(Laporan.laporanArrayList.get(i).getRusak_berat() == 0) {
                    i++;
                } else {
                    MyItem myItem = new MyItem(Laporan.laporanArrayList.get(i).getLokasi().latitude, Laporan.laporanArrayList.get(i).getLokasi().longitude);
                    myItem.setId(i);
                    mClusterManager.addItem(myItem);
                    list.add(Laporan.laporanArrayList.get(i).getLokasi());
                }
            }

            if (Laporan.laporanArrayList.size()==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Laporan.laporanArrayList.get(0).getLokasi(), 5));
            }
        } catch (NullPointerException e){
            e.getStackTrace();
        }
    }

    private void ambilDataRusakRingan() {
        list=new ArrayList<>();
        try {
            for(int i = 0; i < Laporan.laporanArrayList.size(); i++) {
                if(Laporan.laporanArrayList.get(i).getRusak_ringan() == 0) {
                    i++;
                } else {
                    MyItem myItem = new MyItem(Laporan.laporanArrayList.get(i).getLokasi().latitude, Laporan.laporanArrayList.get(i).getLokasi().longitude);
                    myItem.setId(i);
                    mClusterManager.addItem(myItem);
                    list.add(Laporan.laporanArrayList.get(i).getLokasi());
                }
            }

            if (Laporan.laporanArrayList.size()==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Laporan.laporanArrayList.get(0).getLokasi(), 5));
            }
        } catch (NullPointerException e){
            e.getStackTrace();
        }
    }
}