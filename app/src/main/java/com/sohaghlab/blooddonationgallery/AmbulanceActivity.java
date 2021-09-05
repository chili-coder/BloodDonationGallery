package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.firebase.database.FirebaseDatabase;

import com.sohaghlab.blooddonationgallery.Adapter.AmbulanceAdapter;
import com.sohaghlab.blooddonationgallery.Model.AmbulanceModel;


public class AmbulanceActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    AmbulanceAdapter adapter;
    private ProgressBar progressAmbulance;

    public Toolbar toolbar;
    private InterstitialAd mInterstitialAd;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        toolbar = findViewById(R.id.toolbar_ambulance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.ambulance);
        progressAmbulance = findViewById(R.id.progressAmbulance);

        recyclerview = findViewById(R.id.ambulanceRecycler);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        ///banner ads start

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner_ad_id));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /// banner ads end







        FirebaseRecyclerOptions<AmbulanceModel> options =
                new FirebaseRecyclerOptions.Builder<AmbulanceModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ambulances"), AmbulanceModel.class)
                        .build();


        adapter = new AmbulanceAdapter(options);
        recyclerview.setAdapter(adapter);


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

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        progressAmbulance.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.search_manu, menu);

        MenuItem item = menu.findItem(R.id.serch3);

        SearchView searchView = (SearchView) item.getActionView();

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


        return super.onCreateOptionsMenu(menu);
    }

    private void process_search(String query) {
        String searchtext = query.toLowerCase();
        FirebaseRecyclerOptions<AmbulanceModel> options =
                new FirebaseRecyclerOptions.Builder<AmbulanceModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ambulances").orderByChild("location").startAt(searchtext).endAt(searchtext + "\uf8ff"), AmbulanceModel.class)
                        .build();

        adapter = new AmbulanceAdapter(options);
        adapter.startListening();
        recyclerview.setAdapter(adapter);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
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