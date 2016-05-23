package com.pkm.proyek.seismoalpha;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static boolean umum;
    Button loginButton;
    Button umumButton;
    Button pelaporButton;
    private static EditText username;
    private static EditText password;
    TextInputLayout un_container;
    TextInputLayout pw_container;
    View decorView;
    ImageView logo;
    LinearLayout kontainer;
    public static RelativeLayout loading;
    public static ProgressBar spinning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        umum = false;
        decorView = getWindow().getDecorView();
        umumButton = (Button)findViewById(R.id.umum);
        pelaporButton = (Button)findViewById(R.id.pelapor);
        kontainer = (LinearLayout)findViewById(R.id.kontainer_button);

        loading=(RelativeLayout)findViewById(R.id.loading);
        spinning=(ProgressBar)findViewById(R.id.spinning);

        username    = (EditText) findViewById(R.id.input_username);
        password    = (EditText) findViewById(R.id.input_password);
        loginButton = (Button)findViewById(R.id.login_button);

        logo        = (ImageView) findViewById(R.id.logo);
        un_container= (TextInputLayout) findViewById(R.id.layout_input_username);
        pw_container= (TextInputLayout) findViewById(R.id.layout_input_password);

        umumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                umum = true;
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        pelaporButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo.setScaleY((float) 0.8);
                logo.setScaleX((float) 0.8);
                kontainer.setVisibility(View.GONE);
                un_container.setVisibility(View.VISIBLE);
                pw_container.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                logo.setClickable(false);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });


        //Get MyLocation
        new MyLocation().execute(this);
    }

    private void logIn() {
        loadFromAPI.sync_mode=loadFromAPI.SYNC_MODE_LOGIN;
        new loadFromAPI().execute(
                new Pair<Context, String>(this, username.getText().toString()),
                new Pair<Context, String>(this, password.getText().toString())
        );

        if(username.getText().toString().isEmpty()){
            username.setError(getString(R.string.error_field_required));
            username.requestFocus();
        }
        else if(password.getText().toString().isEmpty()){
            password.setError(getString(R.string.error_field_required));
            password.requestFocus();
        }else{

        }
    }

    public static void startLoad(){
        loading.setVisibility(View.GONE);
        spinning.setVisibility(View.VISIBLE);
    }

    public static void stopLoad(){
        loading.setVisibility(View.VISIBLE);
        spinning.setVisibility(View.GONE);
    }

    public static void wrongAcc(){
        username.setError("Username atau Password Salah");
        username.requestFocus();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
}

