package com.example.synerzip.quiz;

/**
 * Created by Minhasoft on 3/3/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import static android.R.attr.value;
import static android.R.id.message;
import static com.example.synerzip.quiz.R.string.subject;
import static com.example.synerzip.quiz.R.string.to;

public class SendEmailActivity extends Activity {


    int MY_REQUEST_CODE=1;

    private int totalScore;
    private int totalMarks = 0;
    private String name;
    private SharedPreferences mainPref;
    private List<String> mSubjects;
    SharedPreferences.Editor editor = null;

    EditText txtData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_CC, new String[]{""});


       // intent.putExtra(Intent.EXTRA_SUBJECT, "RoamFree Feedback: User Id " + userId);

        intent .putExtra(android.content.Intent.EXTRA_SUBJECT,                            "               I.T Brain Game :-.     ");

        /*intent.putExtra(Intent.EXTRA_SUBJECT, "I.T Brain Game :-. ");*/


       /* *//**//*intent.putExtra(android.content.Intent.EXTRA_SUBJECT, Html.fromHtml(" <b>    I.T Brain Game :-.  </b>" ));*/
        intent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(" <b>  Thankx For Playing With Us .</b>  "));
//        startActivity(Intent.createChooser(intent, "Send email..."));

        startActivityForResult(Intent.createChooser(intent, "Choose an Email client:"), 1);


//        Intent newActivity = new Intent(SendEmailActivity.this, ResultActivity.class);
//        startActivity(newActivity);
    }



//    subject issue



/*
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "some@email.address" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        startActivity(Intent.createChooser(intent, ""));*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == MY_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {


                Toast.makeText(SendEmailActivity.this, "Nothing ..!! ", Toast.LENGTH_SHORT).show();

                /*Intent newActivity = new Intent(SendEmailActivity.this, ResultActivity.class);
                startActivity(newActivity);*/
            }
            else
            {
                Intent newActivity = new Intent(SendEmailActivity.this, ResultActivity.class);
               // newActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newActivity);

                Toast.makeText(SendEmailActivity.this, "Email Send ..!! ", Toast.LENGTH_SHORT).show();

            }
        }
        finish();

       /* super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(SendEmailActivity.this, "Email Send ..!! ", Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(SendEmailActivity.this, ResultActivity.class);
        startActivity(myIntent);
        finish();*/
    }
}







         /*   intent.setType("message/rfc822");
            startActivityForResult(Intent.createChooser(intent, "Choose Email Client"), 0);

            //finish();
            Log.i("Finished sending email...", "");*/




















