package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.GenericLifecycleObserver;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawerLayout;
  public Toolbar toolbar;
  private NavigationView nav_View;
  private CircleImageView navProfile;
  private TextView nav_bloodGroup, nav_name,nav_email,nav_phone,nav_type;
  private DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("BDG");
        getSupportActionBar().getThemedContext();
        toolbar.setTitleTextColor(0xFFFFFFFF);



        drawerLayout = findViewById(R.id.drawerlayout);
        nav_View=findViewById(R.id.navigationbar);
        navProfile=findViewById(R.id.nav_profile);





       ActionBarDrawerToggle  toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout
               , toolbar , R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav_View.setNavigationItemSelectedListener(this);
        navProfile = nav_View.getHeaderView(0).findViewById(R.id.nav_profile);
        nav_name = nav_View.getHeaderView(0).findViewById(R.id.nav_name);
        nav_email = nav_View.getHeaderView(0).findViewById(R.id.nav_email);
        nav_phone = nav_View.getHeaderView(0).findViewById(R.id.nav_phone);
        nav_bloodGroup = nav_View.getHeaderView(0).findViewById(R.id.nav_blood);
        nav_type = nav_View.getHeaderView(0).findViewById(R.id.nav_type);


        userRef= FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name =snapshot.child("name").getValue().toString();
                    nav_name.setText(name);

                    String email =snapshot.child("email").getValue().toString();
                    nav_email.setText(email);

                    String phone =snapshot.child("phone").getValue().toString();
                    nav_phone.setText(phone);

                    String bloodGroup =snapshot.child("bloodgroup").getValue().toString();
                    nav_bloodGroup.setText(bloodGroup);

                    String type =snapshot.child("type").getValue().toString();
                    nav_type.setText(type);

                    String imageUrl =snapshot.child("profileimageurl").getValue().toString();
                    Glide.with(getApplicationContext()).load(imageUrl).into(navProfile);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);





        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}