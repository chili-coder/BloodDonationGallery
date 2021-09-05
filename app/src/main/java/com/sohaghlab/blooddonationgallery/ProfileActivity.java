package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView accountType, editProfile, namePro,phonePro,emailPro,userIdPro,cityPro,bloodGroupPro, agePro;

    ConstraintLayout updateDonationDate;
    private  FirebaseAuth mFirebaseAuth;

    LinearLayout dateDonteLayout;

    TextView sendEmailVerify,verified;
    TextView lastBloodDonation,clickToUpdateDate;
    TextView status;
    TextView setdatetitle;






    private FirebaseAuth mAuth;
    private DatabaseReference userDataRef;
    public Toolbar toolbar;
    private static final  String TAG="ProfileActivity";


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar =findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.profile);




        mFirebaseAuth= FirebaseAuth.getInstance();

        mAuth=FirebaseAuth.getInstance();

      //  updateDonationDate = findViewById(R.id.blooddonationdateupadte);


        profileImage=findViewById(R.id.image_profile);
        accountType=findViewById(R.id.typePro);
        editProfile=findViewById(R.id.editProfile);
        namePro=findViewById(R.id.namePro);
        phonePro=findViewById(R.id.phonePro);
        emailPro=findViewById(R.id.emailPro);
        userIdPro=findViewById(R.id.useIdPro);
        cityPro=findViewById(R.id.cityPro);
        bloodGroupPro=findViewById(R.id.bloodPro);
        verified=findViewById(R.id.verified);
        clickToUpdateDate=findViewById(R.id.setDateTitle);
        lastBloodDonation=findViewById(R.id.lastDonationDatePro);
        status=findViewById(R.id.donatePro);
        setdatetitle=findViewById(R.id.setDateTitle);

       // dateDonteLayout=findViewById(R.id.blooddonationdateupadte);


        //ads in


        ///add out







      /*  onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d(TAG,"onDataSet: date:"+dayOfMonth+"/"+month+"/"+year) ;
                String date = dayOfMonth+"/"+month+"/"+year;
                lastBloodDonation.setText(date);





            }
        };

       */







        sendEmailVerify=findViewById(R.id.noVarification);
        if (!mFirebaseAuth.getCurrentUser().isEmailVerified()){
           sendEmailVerify.setVisibility(View.VISIBLE);
           verified.setVisibility(View.GONE);

        } else{
            sendEmailVerify.setVisibility(View.GONE);
            verified.setVisibility(View.VISIBLE);
        }
        sendEmailVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        new AlertDialog.Builder(ProfileActivity.this)
                                .setTitle("Successfully Send")
                                .setMessage("Chack Your Email Address")
                                .setPositiveButton("ok", null)
                                .show();

                        Toast.makeText(ProfileActivity.this, "Verification Code Send Email", Toast.LENGTH_SHORT).show();
                        sendEmailVerify.setText("Send Code Agin");



                    }
                });
            }
        });








        agePro=findViewById(R.id.agePro);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type =snapshot.child("type").getValue().toString();
                if (type.equals("Donor")){


                    if (snapshot.exists()){
                        accountType.setText(snapshot.child("type").getValue().toString());
                        namePro.setText(snapshot.child("name").getValue().toString());
                        emailPro.setText(snapshot.child("email").getValue().toString());
                        phonePro.setText(snapshot.child("phone").getValue().toString());
                        agePro.setText(snapshot.child("age").getValue().toString());
                        cityPro.setText(snapshot.child("city").getValue().toString());
                        userIdPro.setText(snapshot.child("userid").getValue().toString());
                        status.setText(snapshot.child("status").getValue().toString());
                        setdatetitle.setText(snapshot.child("datetitle").getValue().toString());


                          lastBloodDonation.setText(snapshot.child("lastdonation").getValue().toString());



                        bloodGroupPro.setText(snapshot.child("bloodgroup").getValue().toString());


                        if (snapshot.hasChild("profileimageurl")){
                            Glide.with(getApplicationContext()).load(snapshot.child("profileimageurl").getValue().toString()).into(profileImage);

                        } else {
                            profileImage.setImageResource(R.drawable.user);
                        }



                        editProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ProfileActivity.this,UserDataUpdateActivity.class);
                                startActivity(intent);
                            }
                        });


                    }



                } else {
                    if (snapshot.exists()){
                        accountType.setText(snapshot.child("type").getValue().toString());
                        namePro.setText(snapshot.child("name").getValue().toString());
                        emailPro.setText(snapshot.child("email").getValue().toString());
                        phonePro.setText(snapshot.child("phone").getValue().toString());
                        agePro.setText(snapshot.child("age").getValue().toString());
                        cityPro.setText(snapshot.child("city").getValue().toString());
                        userIdPro.setText(snapshot.child("userid").getValue().toString());
                        status.setText(snapshot.child("status").getValue().toString());
                        setdatetitle.setText(snapshot.child("datetitle").getValue().toString());





                        lastBloodDonation.setText(snapshot.child("lastdonation").getValue().toString());



                        bloodGroupPro.setText(snapshot.child("bloodgroup").getValue().toString());


                        if (snapshot.hasChild("profileimageurl")){
                            Glide.with(getApplicationContext()).load(snapshot.child("profileimageurl").getValue().toString()).into(profileImage);

                        }else{
                            profileImage.setImageResource(R.drawable.user);

                        }



                        editProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ProfileActivity.this,UserReciUpdateActivity.class);
                                startActivity(intent);
                            }
                        });


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









     /*   reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    accountType.setText(snapshot.child("type").getValue().toString());
                    namePro.setText(snapshot.child("name").getValue().toString());
                    emailPro.setText(snapshot.child("email").getValue().toString());
                    phonePro.setText(snapshot.child("phone").getValue().toString());
                    agePro.setText(snapshot.child("age").getValue().toString());
                    cityPro.setText(snapshot.child("city").getValue().toString());
                    userIdPro.setText(snapshot.child("userid").getValue().toString());


                 //   lastBloodDonation.setText(snapshot.child("lastdonation").getValue().toString());



                    bloodGroupPro.setText(snapshot.child("bloodgroup").getValue().toString());


                    Glide.with(getApplicationContext()).load(snapshot.child("profileimageurl").getValue().toString()).into(profileImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      */



    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }



    }


}