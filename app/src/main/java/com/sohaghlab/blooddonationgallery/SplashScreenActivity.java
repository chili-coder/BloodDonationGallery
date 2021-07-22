package com.sohaghlab.blooddonationgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    TextView sohaghlab, developby;
    ImageView imageView;

    Animation top_animation,bottom_animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        sohaghlab=findViewById(R.id.sohaghlab);
        developby=findViewById(R.id.developby);
        imageView=findViewById(R.id.imageView);


        top_animation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_animation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        imageView.setAnimation(top_animation);
        sohaghlab.setAnimation(bottom_animation);
        developby.setAnimation(bottom_animation);



        // transfer login screen
        int SPLASH_SCREEN=3800;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}