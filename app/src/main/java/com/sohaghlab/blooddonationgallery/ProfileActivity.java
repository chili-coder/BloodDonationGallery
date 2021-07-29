package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView accountType, editProfile, namePro,phonePro,emailPro,userIdPro,cityPro,adressPro,bloodGroupPro, agePro;

    LinearLayout updateDonationDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



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
        adressPro=findViewById(R.id.adressPro);
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