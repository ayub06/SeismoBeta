package com.pkm.proyek.seismoalpha.rehabrekon.korban;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pkm.proyek.seismoalpha.R;

public class InputKorban extends Fragment{

    private int index;

    EditText inputAnakL;
    EditText inputAnakP;
    EditText inputDewasaL;
    EditText inputDewasaP;
    EditText inputLansiaL;
    EditText inputLansiaP;
    EditText inputIbuHamil;

    int jmlAnakL = 0;
    int jmlAnakP = 0;
    int jmlDewasaL = 0;
    int jmlDewasaP = 0;
    int jmlLansiaL = 0;
    int jmlLansiaP = 0;
    int jmlIbuHamil = 0;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.korban_input, container, false);
        inputAnakL = (EditText)view.findViewById(R.id.korban_anak_l);
        inputAnakP = (EditText)view.findViewById(R.id.korban_anak_p);
        inputDewasaL = (EditText)view.findViewById(R.id.korban_dewasa_l);
        inputDewasaP = (EditText)view.findViewById(R.id.korban_dewasa_p);
        inputLansiaL = (EditText)view.findViewById(R.id.korban_lansia_l);
        inputLansiaP = (EditText)view.findViewById(R.id.korban_lansia_p);
        inputIbuHamil = (EditText)view.findViewById(R.id.korban_ibu_hamil);
        return view;
    }

    public void getInputData() {
        jmlAnakL=getNumber(inputAnakL.getText().toString());
        jmlAnakP=getNumber(inputAnakP.getText().toString());
        jmlDewasaL = getNumber(inputDewasaL.getText().toString());
        jmlDewasaP = getNumber(inputDewasaP.getText().toString());
        jmlLansiaL = getNumber(inputLansiaL.getText().toString());
        jmlLansiaP = getNumber(inputLansiaP.getText().toString());
        jmlIbuHamil = getNumber(inputIbuHamil.getText().toString());

        Log.d("getInputData",
                jmlAnakL+"\n"
                +jmlAnakP+"\n"
                +jmlDewasaL+"\n"
                +jmlDewasaP+"\n"
                +jmlLansiaL+"\n"+
                +jmlLansiaP+"\n"+
                +jmlIbuHamil
        );

        InputKorbanActivity.korbanArrayList.get(index).insertData(
                jmlAnakL,jmlDewasaL,jmlLansiaL,jmlAnakP,jmlDewasaP,jmlLansiaP,jmlIbuHamil
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
