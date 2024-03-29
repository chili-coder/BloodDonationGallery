package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class DeshboardActivity extends AppCompatActivity {

    ConstraintLayout actvieCard,ambulance,bloodbank,info,profile,nearwithme;


    ImageView arrowBnt,logoutBtn;
    private FirebaseAuth mFirebaseAuth;

    TextView nameDesh,emailDesh,statusDesh,noverified,bloodgroupDesh,typeDesh;
    CircleImageView imageDesh;
    ImageView verifid;

    private InterstitialAd mInterstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deshboard);

       actvieCard=findViewById(R.id.avcive_card);
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
        setAds();






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


///////ads in


        ///add out




        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(DeshboardActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        actvieCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DeshboardActivity.this,CampaignActivity.class);
                startActivity(intent);


            }
        });


        nearwithme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DeshboardActivity.this,CatagorySelectedActivity.class);
                intent.putExtra("group","My Type");
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


                AlertDialog.Builder builder = new AlertDialog.Builder(DeshboardActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.drawable.bdgicon);
                builder.setMessage("Do you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent logout = new Intent(DeshboardActivity.this,LoginActivity.class);
                                startActivity(logout);
                                mFirebaseAuth.signOut();


                                finish();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();







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

    private void setAds() {


        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.admob_ins_ad_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


    }

    @Override
    public void onBackPressed() {

        if (mInterstitialAd!=null){
            mInterstitialAd.show(this);
        }

            super.onBackPressed();

    }
}