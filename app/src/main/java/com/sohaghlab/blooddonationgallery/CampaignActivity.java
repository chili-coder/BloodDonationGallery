package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.sohaghlab.blooddonationgallery.Adapter.AmbulanceAdapter;
import com.sohaghlab.blooddonationgallery.Adapter.BankAdapter;
import com.sohaghlab.blooddonationgallery.Adapter.CampainAdapter;
import com.sohaghlab.blooddonationgallery.Model.AmbulanceModel;
import com.sohaghlab.blooddonationgallery.Model.Bank;
import com.sohaghlab.blooddonationgallery.Model.Campaign;

public class CampaignActivity extends AppCompatActivity {

    RecyclerView camRecyclerView;
    ProgressBar progressBar;
    public Toolbar toolbar;
    CampainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);


        toolbar = findViewById(R.id.toolbar_ambulance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.campaign);
        progressBar = findViewById(R.id.progressAmbulance);


        camRecyclerView=findViewById(R.id.campaignRecyclerView);
        camRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar=findViewById(R.id.progressCam);


        FirebaseRecyclerOptions<Campaign> options =
                new FirebaseRecyclerOptions.Builder<Campaign>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("campaigns"), Campaign.class)
                        .build();


        adapter = new CampainAdapter(options);
        camRecyclerView.setAdapter(adapter);




    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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

        FirebaseRecyclerOptions<Campaign> options =
                new FirebaseRecyclerOptions.Builder<Campaign>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("campaigns").orderByChild("title").startAt(searchtext).endAt(searchtext + "\uf8ff"), Campaign.class)
                        .build();
        adapter = new CampainAdapter(options);
        adapter.startListening();
        camRecyclerView.setAdapter(adapter);

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