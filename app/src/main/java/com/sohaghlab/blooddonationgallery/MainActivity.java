package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sohaghlab.blooddonationgallery.Adapter.AmbulanceAdapter;
import com.sohaghlab.blooddonationgallery.Adapter.UserAdapter;
import com.sohaghlab.blooddonationgallery.Model.AmbulanceModel;
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
    private String title ="";

  private  FirebaseAuth mFirebaseAuth;

  private  TextView donateTextFix;

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

        donateTextFix=findViewById(R.id.donateDateFix);



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

                    if (snapshot.hasChild("profileimageurl")){

                        Glide.with(getApplicationContext()).load(snapshot.child("profileimageurl").getValue().toString()).into(navProfile);


                     //   String imageUrl =snapshot.child("profileimageurl").getValue().toString();
                       // Glide.with(getApplicationContext()).load(imageUrl).into(navProfile);

                    } else {
                        navProfile.setImageResource(R.drawable.user);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        ///no internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo == null || !networkInfo.isConnected()|| !networkInfo.isAvailable()){

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.no_internet_item);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;

            Button retry = dialog.findViewById(R.id.retry);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();

        } else {

        } //end retry




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


            case
                    R.id.deshboard:
                Intent desh = new Intent(MainActivity.this,DeshboardActivity.class);
                startActivity(desh);
                break;






            case R.id.a_negative:
                Intent intent1 = new Intent(MainActivity.this,CatagorySelectedActivity.class);
                intent1.putExtra("group","A-");
                startActivity(intent1);
                break;
            case R.id.b_negative:
                Intent intentA = new Intent(MainActivity.this,CatagorySelectedActivity.class);
                intentA.putExtra("group","B-");
                startActivity(intentA);
                break;
            case R.id.o_negative:
                Intent intent2 = new Intent(MainActivity.this,CatagorySelectedActivity.class);
                intent2.putExtra("group","O-");
                startActivity(intent2);
                break;
            case R.id.ab_negative:
                Intent intent3 = new Intent(MainActivity.this,CatagorySelectedActivity.class);
                intent3.putExtra("group","AB-");
                startActivity(intent3);
                break;
            case R.id.a_positive:
                Intent intent4 = new Intent(MainActivity.this,CatagorySelectedActivity.class);
                intent4.putExtra("group","A+");
                startActivity(intent4);
                break;
            case R.id.b_positive:
                Intent intent5 = new Intent(MainActivity.this,CatagorySelectedActivity.class);
                intent5.putExtra("group","B+");
                startActivity(intent5);
                break;
            case R.id.o_positive:
                Intent intent6 = new Intent(MainActivity.this,CatagorySelectedActivity.class);
                intent6.putExtra("group","O+");
                startActivity(intent6);
                break;
            case R.id.ab_positive:
                Intent intent7 = new Intent(MainActivity.this,CatagorySelectedActivity.class);
                intent7.putExtra("group","AB+");
                startActivity(intent7);
                break;


           case R.id.logout:
               Intent logout = new Intent(MainActivity.this,LoginActivity.class);
               startActivity(logout);
               mFirebaseAuth.signOut();
               finish();
                break;
            default:
                break;






        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        getMenuInflater().inflate(R.menu.manu2,menu2);


        MenuItem item =menu2.findItem(R.id.serch_manu2);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                process_search(query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                process_search(query);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu2);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case
                R.id.about2:
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                break;

            case
                R.id.share2:
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();

                break;

            case
                    R.id.serch_manu2:



                         break;


            default:
                break;

        }


        return true;
    }


    private void process_search(String query) {




        userList = new ArrayList<>();
        userAdapter = new UserAdapter(MainActivity.this,userList);
        recyclerView.setAdapter(userAdapter);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        String searchtext = query.toLowerCase();
        FirebaseRecyclerOptions<AmbulanceModel> options =
                new FirebaseRecyclerOptions.Builder<AmbulanceModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ambulances").orderByChild("location").startAt(searchtext).endAt(searchtext + "\uf8ff"), AmbulanceModel.class)
                        .build();














    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.exit_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }



}