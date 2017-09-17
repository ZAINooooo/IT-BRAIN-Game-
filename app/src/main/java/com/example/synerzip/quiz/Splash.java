package com.example.synerzip.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Minhasoft on 2/20/2017.
 */

@SuppressWarnings("ALL")
public class Splash extends Activity {


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Thread splashTread;


        //logTokens(this);

        //hiding the title bar while showing the splash screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);


        if(isWorkingInternetPersent()){
            splash();
        }
        else{
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
            dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.cross);
            showAlertDialog(Splash.this, "Internet Connection", "This Application requires Internet Connection ", false);
        }


        StartAnimations();
    }


    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }





            /*
                         * Showing splash screen with a timer. This will be useful when you
                         * want to show case your app logo / company
                         */
    public void splash() {
        Thread timerTread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerTread.start();
    }



    public boolean isWorkingInternetPersent() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        // alertDialog.setIcon((status) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
                System.exit(0);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

//        //creating the new thread and runing the timer on it
//        Thread splashTread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    int waited = 0;
//                    // Splash screen pause time
//                    while (waited < 5500) {            // 5.5 seconds= 5500 ms ......  1000 ms = 1 seconds
//                        sleep(100);
//
//                        waited = waited + 100;
//
//                        //setText("Loading...");
//
////                        if(waited < 5000)
////                        {
//////                            setText("Loading...");
////                        }
////                        else if(waited >= 5000 && waited < 6000 )
////                        {
////                            setText("Loading..");
////                        }
////
////                        else if (waited >= 6000)
////                        {
////                            setText("Loading...");
////                        }
////                          waited= waited + 100;
//                    }
//                    Intent intent = new Intent(Splash.this, LogInActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(intent);
//                    Splash.this.finish();
//
//                } catch (InterruptedException e) {
//                    // do nothing
//                } finally {
//                    Splash.this.finish();
//                }
//
//            }
//        };
//        splashTread.start();
//
//    }





//    //checking whether the internet or wifi is connected or not . if not then it will prompt the use to enable it or quit
//
//    public boolean isConnected(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
//            return true;
//        } else {
//            showDialog();
//            return false;
//        }
//    }
//
//    private void showDialog()
//    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Connect to wifi or quit")
//                .setCancelable(false)
//                .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                    }
//                })
//                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        finish();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
    }





