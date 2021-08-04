package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class CatagorySelectedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public Toolbar toolbar;
    private List<User> userList;
    private UserAdapter userAdapter;
    private ProgressBar progressBar;
    private String title ="";
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

            readUsers();

        }




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
                                    .setTitle("Not Found!")
                                    .setMessage("No register yet")
                                    .setPositiveButton("ok", null)
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
}