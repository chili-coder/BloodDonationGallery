package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohaghlab.blooddonationgallery.Adapter.BankAdapter;
import com.sohaghlab.blooddonationgallery.Model.Bank;

import java.util.ArrayList;
import java.util.List;

public class BloodBankActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<Bank> bank;
    BankAdapter bankAdapter;
    DatabaseReference databaseReference;
    public Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);

        toolbar =findViewById(R.id.toolbar_bank);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.bloodbank);

        recyclerView=findViewById(R.id.bloodBankRecy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bank=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("banks");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Bank fetchDatalist=ds.getValue(Bank.class);
                    bank.add(fetchDatalist);
                }

                bankAdapter=new BankAdapter(bank);
                recyclerView.setAdapter(bankAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}