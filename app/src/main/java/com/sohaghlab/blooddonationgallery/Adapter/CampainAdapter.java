package com.sohaghlab.blooddonationgallery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohaghlab.blooddonationgallery.CampainDeshActivity;
import com.sohaghlab.blooddonationgallery.Model.Campaign;
import com.sohaghlab.blooddonationgallery.R;

import java.util.List;

public class CampainAdapter extends FirebaseRecyclerAdapter<Campaign,CampainAdapter.myCampaignHolder> {

    boolean testclick=false;
    DatabaseReference likereference;
    Context context;

    List<Campaign>items;

    public CampainAdapter(@NonNull FirebaseRecyclerOptions<Campaign> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myCampaignHolder holder, int position, @NonNull Campaign model) {
        holder.camTitle.setText(model.getTitle());
        holder.camTime.setText(model.getTime());
        holder.camDate.setText(model.getDate());
        holder.decription.setText(model.getDes());



      ///  boolean isVisible = items.get(position).isVisivility();
   //     holder.constraintLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);






        Glide.with(holder.camImage.getContext()).load(model.getImg()).into(holder.camImage);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userid=firebaseUser.getUid();
        String postkey= getRef(position).getKey();

        holder.getlikebuttonstatus(postkey,userid);

        likereference=FirebaseDatabase.getInstance().getReference("likes");








        holder.camFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testclick=true;

                likereference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if (testclick==true){
                          if (snapshot.child(postkey).hasChild(userid)){

                              likereference.child(postkey).removeValue();
                              testclick=false;


                          }else {

                              likereference.child(postkey).child(userid).setValue(true);
                              testclick=false;

                          }

                      }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });








            }
        });

        holder.camShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            


            }
        });




    }

    @NonNull
    @Override
    public myCampaignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campain_item,parent,false);
        return new myCampaignHolder(view);

    }

    public class myCampaignHolder extends RecyclerView.ViewHolder {

        TextView camTime,camDate,camTitle,camLikeNo;
        ImageView camImage,camShare,camFavorite;
        CardView cardpost;

        TextView decription;

        ConstraintLayout constraintLayout;

        DatabaseReference likereference;

        public myCampaignHolder(@NonNull View itemView) {
            super(itemView);

            camDate=(TextView)itemView.findViewById(R.id.camDate);
            camTime=(TextView)itemView.findViewById(R.id.camTime);
            camTitle=(TextView)itemView.findViewById(R.id.camTitle);
            camShare=(ImageView)itemView.findViewById(R.id.camShare);
            camImage=(ImageView)itemView.findViewById(R.id.campainImage);
            camFavorite=(ImageView)itemView.findViewById(R.id.camFavorite);
            camLikeNo=(TextView)itemView.findViewById(R.id.camLikeNumber);
            cardpost=(CardView) itemView.findViewById(R.id.cardPost);
            decription=(TextView)itemView.findViewById(R.id.textDesciption);
            constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.cmConstantLayoutDescip);






        }

        public void getlikebuttonstatus(String postkey, String userid) {

            likereference= FirebaseDatabase.getInstance().getReference("likes");
            likereference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.child(postkey).hasChild(userid)){

                        int likecount =(int) snapshot.child(postkey).getChildrenCount();
                        camLikeNo.setText(likecount+" likes");
                        camFavorite.setImageResource(R.drawable.ic_favorite);


                    } else {
                        int likecount =(int) snapshot.child(postkey).getChildrenCount();
                        camLikeNo.setText(likecount+" likes");
                        camFavorite.setImageResource(R.drawable.ic_unlike);


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}
