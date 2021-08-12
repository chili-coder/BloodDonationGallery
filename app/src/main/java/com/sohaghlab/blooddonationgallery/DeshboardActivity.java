package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeshboardActivity extends AppCompatActivity {

    ConstraintLayout actvie,ambulance,bloodbank,info,profile,nearwithme;


    ImageView arrowBnt,logoutBtn;
    private FirebaseAuth mFirebaseAuth;

    TextView nameDesh,emailDesh,statusDesh,noverified,bloodgroupDesh,typeDesh;
    CircleImageView imageDesh;
    ImageView verifid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deshboard);

       actvie=findViewById(R.id.avcive_card);
        ambulance=findViewById(R.id.ambulance_card);
        bloodbank=findViewById(R.id.bloodbank_card);
        info=findViewById(R.id.bloodinfo_card);
        profile=findViewById(R.id.profile_card);
        nearwithme=findViewById(R.id.nerwithme_card);

        nameDesh=findViewById(R.id.nameDesh);
        emailDesh=findViewById(R.id.emailDesh);
        noverified=findViewById(R.id.NoemailVerifiedDesh);
        bloodgroupDesh=findViewById(R.id.bloodDesh);
        imageDesh=findViewById(R.id.imageDesh);
        verifid=findViewById(R.id.emailVerifid_Desh);
        statusDesh=findViewById(R.id.statusDesh);


        arrowBnt=findViewById(R.id.backDesh);
        logoutBtn=findViewById(R.id.logOutDesh);
        typeDesh=findViewById(R.id.typeDesh);


        mFirebaseAuth= FirebaseAuth.getInstance();


        if (!mFirebaseAuth.getCurrentUser().isEmailVerified()){
           noverified.setVisibility(View.VISIBLE);
            verifid.setVisibility(View.GONE);

        } else{
            noverified.setVisibility(View.GONE);
           verifid.setVisibility(View.VISIBLE);
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );




        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type =snapshot.child("type").getValue().toString();
                if (type.equals("Donor")){


                    if (snapshot.exists()){
                       typeDesh.setText(snapshot.child("type").getValue().toString());
                        nameDesh.setText(snapshot.child("name").getValue().toString());
                        emailDesh.setText(snapshot.child("email").getValue().toString());
                        statusDesh.setText(snapshot.child("status").getValue().toString());
                        bloodgroupDesh.setText(snapshot.child("bloodgroup").getValue().toString());
                        if (snapshot.hasChild("profileimageurl")){
                            Glide.with(getApplicationContext()).load(snapshot.child("profileimageurl").getValue().toString()).into(imageDesh);

                        } else {
                           imageDesh.setImageResource(R.drawable.user);
                        }


                    }



                } else {
                    if (snapshot.exists()){
                        typeDesh.setText(snapshot.child("type").getValue().toString());
                        nameDesh.setText(snapshot.child("name").getValue().toString());
                        emailDesh.setText(snapshot.child("email").getValue().toString());
                        statusDesh.setText(snapshot.child("status").getValue().toString());
                        bloodgroupDesh.setText(snapshot.child("bloodgroup").getValue().toString());
                        if (snapshot.hasChild("profileimageurl")){
                            Glide.with(getApplicationContext()).load(snapshot.child("profileimageurl").getValue().toString()).into(imageDesh);

                        } else {
                            imageDesh.setImageResource(R.drawable.user);
                        }






                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeshboardActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


        nearwithme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeshboardActivity.this,CatagorySelectedActivity.class);
                startActivity(intent);
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeshboardActivity.this,AmbulanceActivity.class);
                startActivity(intent);
            }
        });

        bloodbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeshboardActivity.this,BloodBankActivity.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeshboardActivity.this,PersonActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(DeshboardActivity.this,LoginActivity.class);
                startActivity(logout);
                mFirebaseAuth.signOut();
                finish();
            }
        });

        arrowBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DeshboardActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}