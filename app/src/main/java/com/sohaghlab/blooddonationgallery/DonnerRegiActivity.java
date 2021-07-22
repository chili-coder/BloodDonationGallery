package com.sohaghlab.blooddonationgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DonnerRegiActivity extends AppCompatActivity {

    TextView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donner_regi);

        signIn=findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonnerRegiActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}