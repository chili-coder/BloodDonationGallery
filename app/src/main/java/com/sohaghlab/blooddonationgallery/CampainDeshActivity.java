package com.sohaghlab.blooddonationgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sohaghlab.blooddonationgallery.Model.Campaign;

public class CampainDeshActivity extends AppCompatActivity {

    TextView titleCam, dateCam, timeCam,dectipCam;
    ImageView imgCam;
    Campaign campaign =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campain_desh);

        timeCam=findViewById(R.id.timeCamDesh);
        dateCam=findViewById(R.id.dateCamDesh);
        titleCam=findViewById(R.id.camDesTitle);
        imgCam=findViewById(R.id.imageDesh);
        dectipCam=findViewById(R.id.descriptionCamDesh);

        /*
        imgCam.setImageResource(getIntent().getIntExtra("img",0));
        titleCam.setText(getIntent().getStringExtra("title"));
        dateCam.setText(getIntent().getStringExtra("date"));
        timeCam.setText(getIntent().getStringExtra("time"));

         */


        /*final  Object object = getIntent().getSerializableExtra("details");

        if (object instanceof Campaign){
            campaign = (Campaign) object;
        }

        if (campaign != null){
            Glide.with(getApplicationContext()).load(campaign.getImg()).into(imgCam);
            titleCam.setText(campaign.getTitle());
            dateCam.setText(campaign.getDate());
            timeCam.setText(campaign.getTime());
        }

         */


      /*  Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title"); //this for string
       String date = bundle.getString("date");
       String time = bundle.getString("date");

       titleCam.setText(title);
       dateCam.setText(date);
       timeCam.setText(time);

       */

        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        titleCam.setText(str);




    }



}