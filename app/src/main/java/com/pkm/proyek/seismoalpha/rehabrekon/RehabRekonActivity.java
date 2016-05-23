package com.pkm.proyek.seismoalpha.rehabrekon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.rehabrekon.kerusakan.InputKerusakanActivity;
import com.pkm.proyek.seismoalpha.rehabrekon.korban.InputKorbanActivity;

public class RehabRekonActivity extends AppCompatActivity {

    private Button korban;
    private Button kerusakan;
    private Button bantuan;

    private ImageView korban_ch;
    private ImageView kerusakan_ch;
    private ImageView bantuan_ch;

    public static boolean korban_checked=false;
    public static boolean kerusakan_checked=false;
    public static boolean bantuan_checked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new);

        korban      =(Button) findViewById(R.id.rr_korban);
        kerusakan   =(Button) findViewById(R.id.rr_kerusakan);
        bantuan     =(Button) findViewById(R.id.rr_bantuan);

        korban_ch   =(ImageView) findViewById(R.id.korban_ch);
        kerusakan_ch=(ImageView) findViewById(R.id.kerusakan_ch);
        bantuan_ch  =(ImageView) findViewById(R.id.bantuan_ch);

        setChecked();

        korban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InputKorbanActivity.class));
            }
        });
        kerusakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InputKerusakanActivity.class));
            }
        });
        bantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setChecked() {
        if(korban_checked){
            korban_ch.setVisibility(View.VISIBLE);
        }else {
            korban_ch.setVisibility(View.GONE);
        }
        if(kerusakan_checked){
            kerusakan_ch.setVisibility(View.VISIBLE);
        }else {
            kerusakan_ch.setVisibility(View.GONE);
        }
        if(bantuan_checked){
            bantuan_ch.setVisibility(View.VISIBLE);
        }else {
            bantuan_ch.setVisibility(View.GONE);
        }
    }
}
