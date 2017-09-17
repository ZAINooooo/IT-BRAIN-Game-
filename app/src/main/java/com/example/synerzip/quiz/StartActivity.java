package com.example.synerzip.quiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Snehal Tembare on 6/7/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class StartActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private SharedPreferences.Editor editor;
    private static final int RC_LOG_IN = 64206;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());




        TextView textView = (TextView) findViewById(R.id.idInstructionTitle);
        SpannableString content = new SpannableString("Instructions");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        SharedPreferences splashPre = getApplicationContext().getSharedPreferences(getString(R.string.quiz), Context.MODE_PRIVATE);
        editor = splashPre.edit();


        String key = splashPre.getString(getString(R.string.key), "");
        String userName;
        if (key.equals(String.valueOf(RC_LOG_IN))) {
            userName = splashPre.getString(getString(R.string.name), "");
        } else {
            userName = getIntent().getStringExtra(getString(R.string.name));
        }

              //gmail user name shown
        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setText(getString(R.string.welcome_text) + " " + userName);
        editor.putString(getString(R.string.name), userName);
        editor.commit();


        Button btnStart = (Button) findViewById(R.id.button_start_quiz);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Thank you for using our app", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(StartActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
//                Intent intent = new Intent(StartActivity.this, MenuActivity.class);
//                startActivity(intent);
//                finish();





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(StartActivity.this);
        dialog.setMessage(getString(R.string.exit_app_message));

        dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                LoginManager.getInstance().logOut();

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        editor.clear();
                        editor.commit();
                        finish();
                    }
                });
            }
        });
        dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
