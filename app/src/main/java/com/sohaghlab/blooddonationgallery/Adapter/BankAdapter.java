package com.sohaghlab.blooddonationgallery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.sohaghlab.blooddonationgallery.Model.AmbulanceModel;
import com.sohaghlab.blooddonationgallery.Model.Bank;
import com.sohaghlab.blooddonationgallery.R;

import java.util.List;

public class BankAdapter extends FirebaseRecyclerAdapter<Bank,BankAdapter.bankviewholder> {


    public BankAdapter(@NonNull FirebaseRecyclerOptions<Bank> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull BankAdapter.bankviewholder holder, int position, @NonNull Bank model) {

        holder.nameText.setText(model.getBankname());
        holder.bankcityText.setText(model.getBankcity());
        holder.phonetext.setText(model.getBankphone());
        holder.locationText.setText(model.getBanklocation());


    }

    @NonNull
    @Override
    public BankAdapter.bankviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_bank_item,parent,false);
        return new BankAdapter.bankviewholder(view);
    }

    class bankviewholder extends RecyclerView.ViewHolder{


        TextView nameText,phonetext,locationText, bankcityText;

        public bankviewholder(@NonNull View itemView) {

            super(itemView);


            nameText=itemView.findViewById(R.id.nameBank);
            phonetext=itemView.findViewById(R.id.phoneBank);
            locationText=itemView.findViewById(R.id.locationBank);
            bankcityText=itemView.findViewById(R.id.cityBank);


        }
    }


}
