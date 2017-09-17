package com.example.synerzip.quiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Snehal Tembare on 26/7/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */

public class ResultActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {

    //    private int totalMarks;
    private SharedPreferences.Editor editor;
    private GoogleApiClient mGoogleApiClient;

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        CallbackManager mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_result);




        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();



        TextView tvUserName = (TextView) findViewById(R.id.text_user_name);
        TextView tvGrade = (TextView) findViewById(R.id.text_grade);
        TextView tvTotalMarks = (TextView) findViewById(R.id.text_total_marks);
        Button btnExit = (Button) findViewById(R.id.button_exit);


        TextView textView = (TextView)findViewById(R.id.text_result);
        SpannableString content = new SpannableString("Result");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);



        SharedPreferences resultPref = getApplicationContext().getSharedPreferences(getString(R.string.quiz), Context.MODE_PRIVATE);
        editor = resultPref.edit();


        String name = resultPref.getString(getString(R.string.name), "");
        //  int score=resultPref.getInt(getString(R.string.total),0);
//        totalMarks=resultPref.getInt(getString(R.string.subcount),0);
//        totalMarks=totalMarks*10;




                          //username
                     tvUserName.setText(name);           //username

                          //total marks
                     tvTotalMarks.setText(getString(R.string.completed));    //You have completed Quiz

                          //grade
                     tvGrade.setText(getString(R.string.thanks));            //Thanks…!!!





        btnExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Quiz App");


                // set dialog message
                alertDialogBuilder.setMessage("Click yes to exit!").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // LoginManager.getInstance().logOut();

                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        if(accessToken != null)
                        {
                            //AccessToken.setCurrentAccessToken(null);
                            LoginManager.getInstance().logOut();
                        }


                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>()
                        {
                            @Override
                            public void onResult(@NonNull Status status)
                            {

                            }
                        });

                        editor.clear();
                        editor.apply();
                        finish();

                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface dialog, int id)
                  {

                      dialog.cancel();
                  }
                });

                            // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                alertDialog.show();
            }


//                LoginManager.getInstance().logOut();
//
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>()
//                {
//                    @Override
//                    public void onResult(@NonNull Status status) {
//
//                    }
//                });
//
//                editor.clear();
//                editor.apply();
//                finish();
            });
        };


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        LoginManager.getInstance().logOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>()
        {
            @Override
            public void onResult(@NonNull Status status) {editor.clear();
                editor.apply();
                finish();

            }
        });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }
}
