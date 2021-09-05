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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.FirebaseDatabase;
import com.sohaghlab.blooddonationgallery.Adapter.AmbulanceAdapter;
import com.sohaghlab.blooddonationgallery.Adapter.BankAdapter;
import com.sohaghlab.blooddonationgallery.Adapter.CampainAdapter;
import com.sohaghlab.blooddonationgallery.Adapter.NotificationAdapter;
import com.sohaghlab.blooddonationgallery.Model.AmbulanceModel;
import com.sohaghlab.blooddonationgallery.Model.Bank;
import com.sohaghlab.blooddonationgallery.Model.Campaign;
import com.sohaghlab.blooddonationgallery.Model.NotificationModel;

import static android.content.ContentValues.TAG;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerviewNoti;
    NotificationAdapter adapterNoti;
    private ProgressBar progressNoti;

    public Toolbar toolbar;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        toolbar = findViewById(R.id.toolbar_ambulance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.notification);
        progressNoti = findViewById(R.id.progressNoti);


        recyclerviewNoti=findViewById(R.id.notificationRecyclerView);
        recyclerviewNoti.setLayoutManager(new LinearLayoutManager(this));
        progressNoti=findViewById(R.id.progressNoti);


        FirebaseRecyclerOptions<NotificationModel> options =
                new FirebaseRecyclerOptions.Builder<NotificationModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("notification"), NotificationModel.class)
                        .build();


        adapterNoti = new NotificationAdapter(options);
        recyclerviewNoti.setAdapter(adapterNoti);

        setAds();




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
        adapterNoti.startListening();
       progressNoti.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterNoti.stopListening();
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (mInterstitialAd!=null){
                    mInterstitialAd.show(this);
                }
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }


    }


}