package com.pkm.proyek.seismoalpha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
public class InputUmum extends AppCompatActivity {
    EditText inputMeninggalL;
    EditText inputMeninggalP;
    EditText inputLukaBeratL;
    EditText inputLukaBeratP;
    EditText inputLukaRinganL;
    EditText inputLukaRinganP;
    EditText inputHilangL;
    EditText inputHilangP;
    EditText inputTempat;
    EditText inputAlamat;
    EditText inputPengungsiL;
    EditText inputPengungsiP;
    EditText inputKebutuhan;
    EditText inputKuantiti;

    Button btnFotoBangunan;
    Button btnFotoJalan;
    Button btnFotoJembatan;

    RadioButton radioBangunanHancur;
    RadioButton radioBangunanRusakBerat;
    RadioButton radioBangunanRusakRingan;
    RadioButton radioJalanHancur;
    RadioButton radioJalanRusakBerat;
    RadioButton radioJalanRusakRingan;
    RadioButton radioJembatanHancur;
    RadioButton radioJembatanRusakBerat;
    RadioButton radioJembatanRusakRingan;

    String namaTempat;
    String alamatTempat;
    int jmlMeninggalL;
    int jmlMeninggalP;
    int jmlLukaBeratL;
    int jmlLukaBeratP;
    int jmlLukaRinganL;
    int jmlLukaRinganP;
    int jmlHilangL;
    int jmlHilangP;
    int jmlPengungsiL;
    int jmlPengungsiP;
    String kebutuhan;
    int kuantiti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umum_input);
        inputTempat = (EditText)findViewById(R.id.umum_bantuan_tempat);
        inputAlamat = (EditText)findViewById(R.id.umum_bantuan_alamat);
        inputKebutuhan = (EditText)findViewById(R.id.umum_kebutuhan);
        inputKuantiti = (EditText)findViewById(R.id.umum_kuantiti);
        inputMeninggalL = (EditText)findViewById(R.id.umum_korban_meninggal_l);
        inputMeninggalP = (EditText)findViewById(R.id.umum_korban_meninggal_p);
        inputLukaBeratL = (EditText)findViewById(R.id.umum_luka_berat_l);
        inputLukaBeratP = (EditText)findViewById(R.id.umum_luka_berat_p);
        inputLukaRinganL = (EditText)findViewById(R.id.umum_luka_ringan_l);
        inputLukaRinganP = (EditText)findViewById(R.id.umum_luka_ringan_p);
        inputHilangL = (EditText)findViewById(R.id.umum_hilang_l);
        inputHilangP = (EditText)findViewById(R.id.umum_hilang_p);
        inputPengungsiL = (EditText)findViewById(R.id.umum_jml_pengungsi_l);
        inputPengungsiP = (EditText)findViewById(R.id.umum_jml_pengungsi_p);

        radioBangunanHancur = (RadioButton)findViewById(R.id.radio_bangunan_hancur);
        radioBangunanRusakBerat = (RadioButton)findViewById(R.id.radio_bangunan_rusak_berat);
        radioBangunanRusakRingan = (RadioButton)findViewById(R.id.radio_bangunan_rusak_ringan);
        radioJalanHancur = (RadioButton)findViewById(R.id.radio_jalan_hancur);
        radioJalanRusakBerat = (RadioButton)findViewById(R.id.radio_jalan_rusak_berat);
        radioJalanRusakRingan = (RadioButton)findViewById(R.id.radio_jalan_rusak_ringan);
        radioJembatanHancur = (RadioButton)findViewById(R.id.radio_jembatan_hancur);
        radioJembatanRusakBerat = (RadioButton)findViewById(R.id.radio_jembatan_rusak_berat);
        radioJembatanRusakRingan = (RadioButton)findViewById(R.id.radio_jembatan_rusak_ringan);

        //menghandle kondisi ketika radiobutton diklik
        //dipisahkan antara bangunan, jalan, dan jembatan
        onRadioBangunanClicked();
        onRadioJalanClicked();
        onRadioJembatanClicked();
    }

    private void getInputData() {
        namaTempat = inputTempat.getText().toString();
        alamatTempat = inputAlamat.getText().toString();
        kebutuhan = inputKebutuhan.getText().toString();
        jmlMeninggalL = Integer.valueOf(inputMeninggalL.getText().toString());
        jmlMeninggalL = Integer.valueOf(inputMeninggalL.getText().toString());
        jmlMeninggalP = Integer.valueOf(inputMeninggalP.getText().toString());
        jmlLukaBeratL = Integer.valueOf(inputLukaBeratL.getText().toString());
        jmlLukaBeratP = Integer.valueOf(inputLukaBeratP.getText().toString());
        jmlLukaRinganL = Integer.valueOf(inputLukaRinganL.getText().toString());
        jmlLukaRinganP = Integer.valueOf(inputLukaRinganP.getText().toString());
        jmlHilangL = Integer.valueOf(inputHilangL.getText().toString());
        jmlHilangP = Integer.valueOf(inputHilangP.getText().toString());
        jmlPengungsiL = Integer.valueOf(inputPengungsiL.getText().toString());
        jmlPengungsiP = Integer.valueOf(inputPengungsiP.getText().toString());
        kuantiti = Integer.valueOf(inputKuantiti.getText().toString());
    }

    private void onRadioBangunanClicked(){
        if(radioBangunanHancur.isChecked()){
            //bangunan hancur
        }
        else if(radioBangunanRusakBerat.isChecked()){
            //bangunan rusak berat
        }
        else if(radioBangunanRusakRingan.isChecked()){
            //bangunan rusak ringan
        }
    }

    private void onRadioJalanClicked(){
        if(radioJalanHancur.isChecked()){
            //jalan hancur
        }
        else if(radioJalanRusakBerat.isChecked()){
            //jalan rusak berat
        }
        else if(radioJalanRusakRingan.isChecked()){
            //jalan rusak ringan
        }
    }

    private void onRadioJembatanClicked(){
        if(radioJembatanHancur.isChecked()){
            //jembatan hancur
        }
        else if(radioJembatanRusakBerat.isChecked()){
            //jembatan rusak berat
        }
        else if(radioJembatanRusakRingan.isChecked()){
            //jembatan rusak ringan
        }
    }
}
