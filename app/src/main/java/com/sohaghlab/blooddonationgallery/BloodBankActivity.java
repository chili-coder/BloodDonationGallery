package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohaghlab.blooddonationgallery.Adapter.AmbulanceAdapter;
import com.sohaghlab.blooddonationgallery.Adapter.BankAdapter;
import com.sohaghlab.blooddonationgallery.Model.AmbulanceModel;
import com.sohaghlab.blooddonationgallery.Model.Bank;

import java.util.ArrayList;
import java.util.List;

public class BloodBankActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BankAdapter adapterbank;
    public Toolbar toolbar;
    private ProgressBar progressBank;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        toolbar =findViewById(R.id.toolbar_bank);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.bloodbank);
        progressBank=findViewById(R.id.progressBank);

        recyclerView=findViewById(R.id.bloodBankRecy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<Bank> options =
                new FirebaseRecyclerOptions.Builder<Bank>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("banks"), Bank.class)
                        .build();


        adapterbank = new BankAdapter(options);
        recyclerView.setAdapter(adapterbank);


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
        adapterbank.startListening();
        progressBank.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterbank.stopListening();
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menubank) {
        getMenuInflater().inflate(R.menu.bank_search, menubank);

        MenuItem item = menubank.findItem(R.id.bankSerch);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String querybank) {


                process_search(querybank);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String querybank) {

                process_search(querybank);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menubank);
    }


    private void process_search(String querybank) {

        String searchtext = querybank.toLowerCase();

        FirebaseRecyclerOptions<Bank> options =
                new FirebaseRecyclerOptions.Builder<Bank>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("banks").orderByChild("bankcity").startAt(searchtext).endAt(searchtext + "\uf8ff"), Bank.class)
                        .build();

       adapterbank = new BankAdapter(options);
        adapterbank.startListening();
       recyclerView.setAdapter(adapterbank);


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





}