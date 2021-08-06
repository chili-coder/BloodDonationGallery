package com.sohaghlab.blooddonationgallery.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.sohaghlab.blooddonationgallery.Model.AmbulanceModel;
import com.sohaghlab.blooddonationgallery.R;

public class AmbulanceAdapter extends FirebaseRecyclerAdapter<AmbulanceModel,AmbulanceAdapter.myviewholder> {



    public AmbulanceAdapter(@NonNull FirebaseRecyclerOptions<AmbulanceModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull AmbulanceModel model) {

        holder.name_am.setText(model.getName());
        holder.phone_am.setText(model.getPhone());
        holder.location_am.setText(model.getLocation());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulance_item,parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{


        TextView name_am,location_am,phone_am;

        public myviewholder(@NonNull View itemView) {

            super(itemView);


            name_am=(TextView)itemView.findViewById(R.id.nameBank);
            phone_am=(TextView)itemView.findViewById(R.id.phoneBank);
            location_am=(TextView)itemView.findViewById(R.id.locationBank);


        }
    }


}
