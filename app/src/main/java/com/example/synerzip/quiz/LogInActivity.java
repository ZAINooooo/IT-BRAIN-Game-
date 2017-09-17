package com.example.synerzip.quiz;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ToolTipPopup;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * Created by Snehal Tembare on 19/7/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class LogInActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private TextView mInfo;
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private SharedPreferences logInPref;
    private Singleton singleton;

    //Client for accessing Google APIs
    private GoogleApiClient mGoogleApiClient;

    private GoogleSignInOptions gso;

    //Basic account information of the signed in Google user
    private GoogleSignInAccount account;

    //Requestcode for resolutions involving sign-in
    private static final int RC_SIGN_IN = 9001;

    //Requestcode for resolutions involving Log-in
    private static final int RC_LOG_IN = 64206;

    //Result of SignIn account potentially contain a GoogleSignInAccount.
    private GoogleSignInResult result;

    //SharedPreferences to pass th information to other activities
    private SharedPreferences.Editor editor;

    private SignInButton btnSignIn;

    private String name;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        webView = (WebView) findViewById(R.id.webview);


//        // Add code to print out the key hash
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.facebook.samples.hellofacebook",
//                    PackageManager.GET_SIGNATURES);
//            for (android.content.pm.Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {
//
//            Toast.makeText(this, "exception  " + ignored.getMessage(),  Toast.LENGTH_SHORT).show();
//
//        }


       // logTokens(this);
        //calculateHashKey(String.valueOf(this));

//
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.back6);
//        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
//
//        ImageView circularImageView = (ImageView)findViewById(R.id.imageView);
//        circularImageView.setImageBitmap(circularBitmap);



        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());


        //GoogleSignInOptions is options used to configure the GOOGLE_SIGN_IN_API.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestProfile().build();
        singleton = Singleton.getInstance();


        singleton.setCFlag(false);
        singleton.setCppFlag(false);
        singleton.setJavaFlag(false);
        singleton.setAndroidFlag(false);
        singleton.setJavaScriptFlag(false);

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        logInPref = getApplicationContext().getSharedPreferences(getString(R.string.quiz), Context.MODE_PRIVATE);
        editor = logInPref.edit();


//        popupWindow.showAtLocation(anyViewOnlyNeededForWindowToken, Gravity.CENTER, 0, 0);
        //final ViewGroup transitionsContainer = (ViewGroup) view.findViewById(R.id.activity_login);


        btnSignIn = (SignInButton) findViewById(R.id.button_sign_in);
        btnSignIn.setScopes(gso.getScopeArray());

        mLoginButton = (LoginButton) findViewById(R.id.button_login);
        mLoginButton.setToolTipStyle(ToolTipPopup.Style.BLACK);





        btnSignIn.setSize(SignInButton.SIZE_WIDE);
        btnSignIn.setScopes(gso.getScopeArray());
        mLoginButton.setReadPermissions(getString(R.string.email));

        btnSignIn.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

    }



//    public static void logTokens(Activity context) {
//
//        // Add code to print out the key hash
//        try {
//            PackageInfo info = context.getPackageManager().getPackageInfo(
//                    context.getApplicationContext().getPackageName(),
//                    PackageManager.GET_SIGNATURES);
//            for (android.content.pm.Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA-1");
//                md.update(signature.toByteArray());
////                Log.e("SHA1-KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
////                Log.e("SHA1-KeyHash:::",md.toString());
//
//                md = MessageDigest.getInstance("MD5");
//                md.update(signature.toByteArray());
////                Log.e("MD5-KeyHash:::",
////                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
//
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.e("SHA-Hex-From-KeyHash:::", bytesToHex(md.digest()));
//                md.update(signature.toByteArray());
//                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {
//        }
//    }






    final protected static char[] hexArray = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }






    private boolean isNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Log.e(getString(R.string.network_available), "");
            return true;
        } else {
            Log.e(getString(R.string.network_not_available), "");
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RC_LOG_IN == requestCode)
        {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else
        {
            if (RC_SIGN_IN == requestCode && 0 != resultCode)
            {


            }

            else
            {
                Toast.makeText(getApplicationContext(), getString(R.string.could_not_sign_in), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

             //Gmail Login
            case R.id.button_sign_in:
                ProgressDialog dialog=new ProgressDialog(LogInActivity.this);
                dialog.setMessage(getString(R.string.please_wait));
                dialog.show();

                dialog.dismiss();

                if (isNetworkAvailable())
                {

//                ProgressDialog dialog=new ProgressDialog(LogInActivity.this);
//                dialog.setMessage(getString(R.string.please_wait));
//                dialog.show();

                Intent in = new Intent(LogInActivity.this, WebViewActivity.class);
                startActivity(in);

//                ProgressDialog dialog=new ProgressDialog(LogInActivity.this);
//                dialog.setMessage(getString(R.string.please_wait));
//                dialog.show();

                    dialog.dismiss();
                finish();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setTitle("No Internet");
                    builder.setMessage("Internet is required. Please Retry.").setIcon(R.drawable.ic_exit_to_app);

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                            //finish();

                        }
                    });

                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //dialog.dismiss();
                            isNetworkAvailable();
                        }

                    });
                    AlertDialog dialogs = builder.create(); // calling builder.create after adding buttons
                    dialogs.show();
                    //Toast.makeText(this, "Network Unavailable!", Toast.LENGTH_LONG).show();
                }
                break;

            //facebook Login
            case R.id.button_login:
                mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (null != response.getError()) {
                                } else {
                                    String firstName = object.optString(getString(R.string.name));
                                    logInPref = getApplicationContext().getSharedPreferences(getString(R.string.quiz), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = logInPref.edit();
                                    editor.putString(getString(R.string.name), firstName);
                                    editor.putString(getString(R.string.key), String.valueOf(RC_LOG_IN));
                                    editor.commit();
                                    Intent intent = new Intent(LogInActivity.this, StartActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).executeAsync();
                    }

                    @Override
                    public void onCancel()
                    {
                        Toast.makeText(getApplicationContext(), getString(R.string.login_cancelled), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error)
                    {
                        Toast.makeText(getApplicationContext(), getString(R.string.login_faild), Toast.LENGTH_SHORT).show();
                    }
                });
                break;

        }
    }

    

    /*TODO
     If facebook application is in phone installed
                profile=Profile.getCurrentProfile();
                mInfo.setText("User ID:" + loginResult.getAccessToken().getUserId() + "\n" + "Token:" + loginResult.getAccessToken().getToken()+profile.getFirstName()+" "+profile.getLastName());
                  mInfo.setText(profile.getName());
                Intent intent=new Intent(LogInActivity.this,StartActivity.class);
                intent.putExtra("NAME",profile.getName());
                startActivity(intent);*/
}


