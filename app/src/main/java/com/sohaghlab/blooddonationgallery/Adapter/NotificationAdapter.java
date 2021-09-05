package com.sohaghlab.blooddonationgallery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.sohaghlab.blooddonationgallery.Model.NotificationModel;
import com.sohaghlab.blooddonationgallery.Model.User;
import com.sohaghlab.blooddonationgallery.R;

import java.util.List;

public class NotificationAdapter extends FirebaseRecyclerAdapter<NotificationModel,NotificationAdapter.notiViewHolder> {

    private List<NotificationModel> userList;



    public NotificationAdapter(@NonNull FirebaseRecyclerOptions<NotificationModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull notiViewHolder holder, int position, @NonNull NotificationModel model) {

        holder.title_noti.setText(model.getTitle());
        holder.des_noti.setText(model.getDes());
        holder.date_noti.setText(model.getDate());
        Glide.with(holder.img_noti.getContext()).load(model.getImg()).error(R.drawable.noti).into(holder.img_noti);




    }

    @NonNull
    @Override
    public notiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
        return new notiViewHolder(view);
    }


    public class notiViewHolder extends RecyclerView.ViewHolder {

        TextView title_noti,date_noti,des_noti;
        ImageView img_noti;


        public notiViewHolder(@NonNull View itemView) {
            super(itemView);

            title_noti=itemView.findViewById(R.id.title_nt);
            des_noti=itemView.findViewById(R.id.description_nt);
            date_noti=itemView.findViewById(R.id.date_nt);
            img_noti=itemView.findViewById(R.id.img_nt);
        }
    }
}
