package com.sohaghlab.blooddonationgallery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sohaghlab.blooddonationgallery.Model.Bank;
import com.sohaghlab.blooddonationgallery.R;

import java.util.List;

public class BankAdapter extends RecyclerView.Adapter {

    List<Bank> bank;
    Context context;

    public BankAdapter(List<Bank> bank) {
        this.bank=bank;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_bank_item,parent,false);
        ViewHolderClass viewHolderClass=new ViewHolderClass(view);
        return viewHolderClass;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass=(ViewHolderClass)holder;
        final Bank bankList=bank.get(position);
        viewHolderClass.nameText.setText(bankList.getBankname());
        viewHolderClass.phonetext.setText(bankList.getBankphone());
        viewHolderClass.locationText.setText(bankList.getBanklocation());
    }

    @Override
    public int getItemCount() {
        return bank.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView nameText,phonetext,locationText;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            nameText=itemView.findViewById(R.id.nameBank);
           phonetext=itemView.findViewById(R.id.phoneBank);
            locationText=itemView.findViewById(R.id.locationBank);

        }
    }
}
