package com.pkm.proyek.seismoalpha.rehabrekon.kerusakan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pkm.proyek.seismoalpha.R;

public class InputKerusakanLain extends Fragment {


    EditText inputRusakKios;
    EditText inputRusakBangunan;
    EditText inputRusakJembatan;
    EditText inputRusakJalan;
    EditText inputRusakSawah;
    EditText inputRusakTebing;
    EditText inputRusakTalud;
    EditText inputRusakKebun;
    EditText inputRusakKolam;
    EditText inputRusakIrigasi;

    int jmlRusakKios = 0;
    int jmlRusakBangunan = 0;
    int jmlRusakJembatan = 0;
    int jmlRusakJalan = 0;
    int jmlRusakSawah = 0;
    int jmlRusakTebing = 0;
    int jmlRusakTalud = 0;
    int jmlRusakKebun = 0;
    int jmlRusakKolam = 0;
    int jmlRusakIrigasi = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.kerusakan_lain_input, container, false);

        inputRusakKios = (EditText)view.findViewById(R.id.kerusakan_lain_kios);
        inputRusakBangunan = (EditText)view.findViewById(R.id.kerusakan_lain_bangunan);
        inputRusakJembatan = (EditText)view.findViewById(R.id.kerusakan_lain_jembatan);
        inputRusakJalan = (EditText)view.findViewById(R.id.kerusakan_lain_jalan);
        inputRusakSawah = (EditText)view.findViewById(R.id.kerusakan_lain_sawah);
        inputRusakTebing = (EditText)view.findViewById(R.id.kerusakan_lain_tebing);
        inputRusakTalud = (EditText)view.findViewById(R.id.kerusakan_lain_talud);
        inputRusakKebun = (EditText)view.findViewById(R.id.kerusakan_lain_kebun);
        inputRusakKolam = (EditText)view.findViewById(R.id.kerusakan_lain_kolam);
        inputRusakIrigasi = (EditText)view.findViewById(R.id.kerusakan_lain_irigasi);
        return view;
    }

    public void getInputData(){
        jmlRusakKios = getNumber(inputRusakKios.getText().toString());
        jmlRusakBangunan = getNumber(inputRusakBangunan.getText().toString());
        jmlRusakJembatan = getNumber(inputRusakJembatan.getText().toString());
        jmlRusakJalan = getNumber(inputRusakJalan.getText().toString());
        jmlRusakSawah = getNumber(inputRusakSawah.getText().toString());
        jmlRusakTebing = getNumber(inputRusakTebing.getText().toString());
        jmlRusakTalud = getNumber(inputRusakKebun.getText().toString());
        jmlRusakKebun = getNumber(inputRusakKebun.getText().toString());
        jmlRusakKolam = getNumber(inputRusakKolam.getText().toString());
        jmlRusakIrigasi = getNumber(inputRusakIrigasi.getText().toString());
    }

    private int getNumber(String text){
        if (text.equals("")){
            return 0;
        }else {
            return Integer.parseInt(text);
        }
    }

    public void saveToDB(){

    }
}
