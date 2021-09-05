package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
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

import static android.content.ContentValues.TAG;

public class CatagorySelectedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public Toolbar toolbar;
    private List<User> userList;
    private UserAdapter userAdapter;
    private ProgressBar progressBar;
    private String title ="";
    private String text="Active";

    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_selected);

        toolbar =findViewById(R.id.toolbar_main2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerview2);
        progressBar=findViewById(R.id.progressbar2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(CatagorySelectedActivity.this,userList);
        recyclerView.setAdapter(userAdapter);

       if (getIntent().getExtras() !=null){

            title=getIntent().getStringExtra("group");
            getSupportActionBar().setTitle("Blood Group "+title);



            if (title.equals("My Type")){
                getNearWithMe();
                getSupportActionBar().setTitle("My Type");
              //  progressBar.setVisibility(View.GONE);

            }else {
                readUsers();

            }


        }


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

        ///add out




        ///no internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

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





    private void getNearWithMe() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String result;
                String type = snapshot.child("type").getValue().toString();
                if (type.equals("Donor")){

                    result ="Recipient";

                } else {
                    result="Donor";

                }

                String citY=snapshot.child("city").getValue().toString();
                String blooDgroup=snapshot.child("bloodgroup").getValue().toString();

                DatabaseReference reference1 =FirebaseDatabase.getInstance().getReference()
                        .child("users");
                Query query = reference1.orderByChild("search").equalTo(result+blooDgroup);


                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            User user =dataSnapshot.getValue(User.class);
                            userList.add(user);
                            progressBar.setVisibility(View.GONE);
                        }
                        userAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                        if (userList.isEmpty()){

                            new AlertDialog.Builder(CatagorySelectedActivity.this)
                                    .setTitle(R.string.notfound)
                                    .setMessage(R.string.noregisteryet)
                                    .setPositiveButton(R.string.ok, null)
                                    .show();



                            progressBar.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void readUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String result;
                String type = snapshot.child("type").getValue().toString();
                if (type.equals("Donor")){

                    result ="Recipient";

                } else {
                    result="Donor";

                }

                DatabaseReference reference1 =FirebaseDatabase.getInstance().getReference()
                        .child("users");
                Query query = reference1.orderByChild("search").equalTo(result+title);


                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            User user =dataSnapshot.getValue(User.class);
                            userList.add(user);
                            progressBar.setVisibility(View.GONE);
                        }
                        userAdapter.notifyDataSetChanged();

                        if (userList.isEmpty()){

                            new AlertDialog.Builder(CatagorySelectedActivity.this)
                                    .setTitle(R.string.notfound)
                                    .setMessage(R.string.noregisteryet)
                                    .setPositiveButton(R.string.ok, null)
                                    .show();



                            progressBar.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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

    @Override
    public void onBackPressed() {

        if (mInterstitialAd!=null){
            mInterstitialAd.show(this);
        }


        super.onBackPressed();
    }
}