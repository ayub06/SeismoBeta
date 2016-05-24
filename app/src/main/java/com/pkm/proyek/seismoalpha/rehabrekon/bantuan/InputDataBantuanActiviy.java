package com.pkm.proyek.seismoalpha.rehabrekon.bantuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.rehabrekon.RehabRekonActivity;

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

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RehabRekonActivity.bantuan_checked=true;
                startActivity(new Intent(getApplicationContext(), RehabRekonActivity.class));
            }
        });
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
