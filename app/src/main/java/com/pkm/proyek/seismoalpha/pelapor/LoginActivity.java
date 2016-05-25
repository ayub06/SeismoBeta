package com.pkm.proyek.seismoalpha.pelapor;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.pkm.proyek.seismoalpha.R;
import com.pkm.proyek.seismoalpha.util.loadFromAPI;
import com.pkm.proyek.seismoalpha.main.MainActivity;
import com.pkm.proyek.seismoalpha.maps.MyLocation;
import com.pkm.seismosense.backend.pelaporApi.PelaporApi;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;

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
    LinearLayout kontainerLogo;
    public static RelativeLayout loading;
    public static ProgressBar spinning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reqPermission();

        umum = false;
        decorView = getWindow().getDecorView();
        umumButton = (Button)findViewById(R.id.umum);
        pelaporButton = (Button)findViewById(R.id.pelapor);
        kontainer = (LinearLayout)findViewById(R.id.kontainer_button);
        kontainerLogo = (LinearLayout)findViewById(R.id.logodiktiugm);

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
                startLoad();
                configGoogleSignIn();
                signIn();
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        pelaporButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo.setScaleY((float) 0.8);
                logo.setScaleX((float) 0.8);
                kontainer.setVisibility(View.GONE);
                kontainerLogo.setVisibility(View.GONE);
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

    /**
     * GOOGLE SIGN IG -----------------------------------------------------
     */

    private void reqPermission() {
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void configGoogleSignIn(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signIn() {
        showProgressDialog();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                //Set MyAccount
                new LoadProfileImage().execute(getURLPhoto(acct.zzmI()));
                if (acct.getId()!=null) {

                    Pelapor.akunIni=new Pelapor(
                            getURLPhoto(acct.zzmI()),
                            6,
                            acct.getDisplayName(),
                            acct.getId()
                    );

                    Log.d("FOTO URL",Pelapor.akunIni.getUrlFoto());
                    Log.d("MY ACCOUNT","SUCCESS");
                }else {
                    Log.d("MY ACCOUNT","GAGAL");
                }
            }
        }
    }

    private String getURLPhoto(String zzmI) {
        //Getting URL PHOTO
        StringTokenizer token= new StringTokenizer(zzmI, ",");
        token.nextToken();//ID
        token.nextToken();//EMAIL
        token.nextToken();//NAMA
        String urlPhoto=token.nextToken();
        urlPhoto=urlPhoto.replaceAll("\"photoUrl\":","");
        urlPhoto=urlPhoto.replaceAll("\"","");
        urlPhoto=urlPhoto.replaceAll("/", "");
        urlPhoto=urlPhoto.replace((char) 92, (char) 47);
        Log.d("URL PHOTO",urlPhoto+"?sz=100");
        return urlPhoto+"?sz=100";
    }

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            if(urldisplay.contains("https")){
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result==null){

                //User.myAccount.setFoto(BitmapFactory.decodeResource(getResources(),
                //        R.drawable.ic_account_circle_white_24dp));
                Log.d("AKUN FOTO","DEFAULT");
            }else {
                Log.d("AKUN FOTO", "ADA");
                Pelapor.akunIni.setFoto(result);
            }
            if (!isRegistered()){
                //showProgressDialog();
                //new RegisterNewUser().execute(Pelapor.akunIni);
            }else {
                hideProgressDialog();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
    }

    private boolean isRegistered() {
        //Check with loadFromAPI loginPelapor, if 1 return true, else 0;
        return true;
    }

    private void showProgressDialog() {
        loginButton.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        loading.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

    class RegisterNewUser extends AsyncTask<Pelapor,Void,Boolean>{
        private PelaporApi pelaporApi =null;

        @Override
        protected Boolean doInBackground(Pelapor... params) {
            if(pelaporApi == null) {
                PelaporApi.Builder builder = new PelaporApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl(loadFromAPI.URL_ROOT)
                        /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        })*/;
                pelaporApi = builder.build();
            }

            //Create Foto to Streamed
            /*ByteArrayOutputStream bos=new ByteArrayOutputStream();
            params[0].getFoto().compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte [] bytestream=bos.toByteArray();*/

            Log.d("USER NAMA",params[0].getNama());

            //Create Produk backend
            com.pkm.seismosense.backend.pelaporApi.model.Pelapor inserted=new com.pkm.seismosense.backend.pelaporApi.model.Pelapor();

            /*inserted.set
            inserted.setNama(params[0].getNama());
            inserted.setFoto(android.util.Base64.encodeToString(bytestream, android.util.Base64.DEFAULT));
            inserted.setEmail(params[0].getEmail());*/

            try {
                String result= String.valueOf(pelaporApi.insert(inserted).execute().getEmail());
                if (result.isEmpty()){
                    Log.d("HASIL",result);
                    return false;
                }else {
                    Log.d("HASIL",result);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
            } catch (IOException e) {
                Log.d("ERROR REGISTER USER",e.getMessage());
                return false;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    /**
     * --------------------------------------------------------------------
     */


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

