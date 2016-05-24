package com.pkm.proyek.seismoalpha.rehabrekon.bantuan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.pkm.proyek.seismoalpha.R;

/**
 * Created by User3887 on 5/24/2016.
 */
public class InputDataBantuanActiviy extends AppCompatActivity {
    EditText inputNamaTempat;
    EditText inputAlamat;
    EditText inputJenisBantuan;
    EditText inputKuantitas;
    EditText inputSumberBantuan;
    EditText inputKeterangan;

    String namaTempat;
    String alamat;
    String jenisBantuan;
    String kuantitas;
    String sumberBantuan;
    String keterangan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bantuan_input);
        inputNamaTempat = (EditText)findViewById(R.id.data_bantuan_nama_tempat);
        inputAlamat = (EditText)findViewById(R.id.data_bantuan_alamat);
        inputJenisBantuan = (EditText)findViewById(R.id.data_bantuan_jenis_bantuan);
        inputKuantitas = (EditText)findViewById(R.id.data_bantuan_kuantitas);
        inputSumberBantuan = (EditText)findViewById(R.id.data_bantuan_sumber_bantuan);
        inputKeterangan = (EditText)findViewById(R.id.data_bantuan_keterangan);
    }

    private void getInputData() {
        namaTempat = inputNamaTempat.getText().toString();
        alamat = inputAlamat.getText().toString();
        jenisBantuan = inputJenisBantuan.getText().toString();
        kuantitas = inputKuantitas.getText().toString();
        sumberBantuan = inputSumberBantuan.getText().toString();
        keterangan = inputKeterangan.getText().toString();
    }
}
