package com.sohaghlab.blooddonationgallery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sohaghlab.blooddonationgallery.Model.User;
import com.sohaghlab.blooddonationgallery.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.ViewHolder> {

private Context context;
private List<User>userList;


    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
       // this.userListFilter = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.profile_layout,parent,false);
       return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = userList.get(position);
        holder.typeRecy.setText(user.getType());

        if (user.getType().equals("Recipient")){
            holder.lastDonationRecy.setVisibility(View.GONE);
        }


        holder.nameRecy.setText(user.getName());
        holder.phoneRecy.setText(user.getPhone());
        holder.bloodRecy.setText(user.getBloodgroup());
        holder.cityRecy.setText(user.getCity());
        holder.lastDonationRecy.setText(user.getLastdonation());

        Glide.with(context).load(user.getProfileimageurl()).into(holder.profileImageRecy);


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView profileImageRecy;
        TextView nameRecy,phoneRecy,cityRecy,ageRecy,lastDonationRecy,bloodRecy,typeRecy;
        ImageView callNowRecy,emailNowRecy;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImageRecy=itemView.findViewById(R.id.profileImageRecy);
            nameRecy=itemView.findViewById(R.id.nameRecy);
            phoneRecy=itemView.findViewById(R.id.phoneRecy);
            cityRecy=itemView.findViewById(R.id.cityRecy);
            bloodRecy=itemView.findViewById(R.id.bloodRecy);
            typeRecy=itemView.findViewById(R.id.typeRecy);
            lastDonationRecy=itemView.findViewById(R.id.lastDonationRecy);
            ageRecy=itemView.findViewById(R.id.ageRecy);

            callNowRecy=itemView.findViewById(R.id.callNowRecy);
            emailNowRecy=itemView.findViewById(R.id.emailNowRecy);
        }
    }
}
