 package com.sohaghlab.blooddonationgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

 public class SelectRegiActivity extends AppCompatActivity {

    Button donner,recipient;
    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_regi);

        donner=findViewById(R.id.donner);
        recipient=findViewById(R.id.recipient);
        signIn=findViewById(R.id.signin);

        donner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectRegiActivity.this,DonnerRegiActivity.class);
                startActivity(intent);
            }
        });

        recipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectRegiActivity.this,RecipientActivity.class);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRegiActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}