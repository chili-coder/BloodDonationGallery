package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sohaghlab.blooddonationgallery.Adapter.UserAdapter;
import com.sohaghlab.blooddonationgallery.Model.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawerLayout;
  public Toolbar toolbar;
  private NavigationView nav_View;
  private CircleImageView navProfile;
  private TextView nav_bloodGroup, nav_name,nav_email,nav_phone,nav_type;
  private DatabaseReference userRef;

  private List<User>userList;
  private UserAdapter userAdapter;
  private ProgressBar progressBar;
  private RecyclerView recyclerView;

  private  FirebaseAuth mFirebaseAuth;

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

        mFirebaseAuth= FirebaseAuth.getInstance();



        drawerLayout = findViewById(R.id.drawerlayout);
        nav_View=findViewById(R.id.navigationbar);
        navProfile=findViewById(R.id.nav_profile);

        progressBar=findViewById(R.id.progressbar);
        recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        userList = new ArrayList<>();
        userAdapter= new UserAdapter(MainActivity.this,userList);
        recyclerView.setAdapter(userAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String type =snapshot.child("type").getValue().toString();
                if (type.equals("Donor")){
                    readRecipients();
                } else {
                    readDonors();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







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

    private void readRecipients() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users");
        Query query =reference.orderByChild("type").equalTo("Recipient");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);

                }
                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if (userList.isEmpty()){

                    Toast.makeText(MainActivity.this, "No Recipient Found!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void readDonors() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users");
        Query query =reference.orderByChild("type").equalTo("Donor");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);

                }
                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if (userList.isEmpty()){

                    Toast.makeText(MainActivity.this, "No Donor Found!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

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
                break;

          //  case R.id.logout:
              //  Intent logout = new Intent(MainActivity.this,LoginActivity.class);
              //  startActivity(logout);
              //  mFirebaseAuth.signOut();
             //   finish();
               // break;






        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}