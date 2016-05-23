package com.pkm.proyek.seismoalpha.rehabrekon.kerusakan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.rehabrekon.korban.InputKorbanActivity;

public class InputKerusakan extends Fragment {

    private int index;

    EditText inputRusakBerat;
    EditText inputRusakSedang;
    EditText inputRusakRingan;

    int jmlRusakBerat = 0;
    int jmlRusakSedang = 0;
    int jmlRusakRingan = 0;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.kerusakan_input, container, false);
        inputRusakBerat = (EditText)view.findViewById(R.id.kerusakan_rusak_berat);
        inputRusakSedang = (EditText)view.findViewById(R.id.kerusakan_rusak_sedang);
        inputRusakRingan = (EditText)view.findViewById(R.id.kerusakan_rusak_ringan);
        return view;
    }

    public void getInputData() {
        jmlRusakBerat = getNumber(inputRusakBerat.getText().toString());
        jmlRusakSedang = getNumber(inputRusakSedang.getText().toString());
        jmlRusakRingan = getNumber(inputRusakRingan.getText().toString());

        InputKerusakanActivity.kerusakanArrayList.get(index).insertData(
                jmlRusakBerat,jmlRusakSedang,jmlRusakRingan
        );
    }


    private int getNumber(String text){
        if (text.equals("")){
            return 0;
        }else {
            return Integer.parseInt(text);
        }
    }


}
