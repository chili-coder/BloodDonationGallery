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
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.FirebaseDatabase;

import com.sohaghlab.blooddonationgallery.Adapter.AmbulanceAdapter;
import com.sohaghlab.blooddonationgallery.Model.AmbulanceModel;


public class AmbulanceActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    AmbulanceAdapter adapter;
    private ProgressBar progressAmbulance;

    public Toolbar toolbar;


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


        FirebaseRecyclerOptions<AmbulanceModel> options =
                new FirebaseRecyclerOptions.Builder<AmbulanceModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ambulances"), AmbulanceModel.class)
                        .build();


        adapter = new AmbulanceAdapter(options);
        recyclerview.setAdapter(adapter);


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
}