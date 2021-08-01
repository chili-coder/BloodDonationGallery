package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView accountType, editProfile, namePro,phonePro,emailPro,userIdPro,cityPro,bloodGroupPro, agePro;

    LinearLayout updateDonationDate;
    private  FirebaseAuth mFirebaseAuth;
    TextView signout;
    TextView sendEmailVerify,verified;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseAuth= FirebaseAuth.getInstance();



        updateDonationDate = findViewById(R.id.BloodDonationDateLayout);


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




       signout=findViewById(R.id.signoutPro);
       signout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
               startActivity(intent);

               mFirebaseAuth.signOut();
               finish();
           }
       });

        agePro=findViewById(R.id.agePro);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );


        reference.addValueEventListener(new ValueEventListener() {
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
                    bloodGroupPro.setText(snapshot.child("bloodgroup").getValue().toString());

                    Glide.with(getApplicationContext()).load(snapshot.child("profileimageurl").getValue().toString()).into(profileImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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