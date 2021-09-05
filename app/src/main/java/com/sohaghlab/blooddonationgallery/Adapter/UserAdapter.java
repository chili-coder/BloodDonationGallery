package com.sohaghlab.blooddonationgallery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.sohaghlab.blooddonationgallery.Model.User;
import com.sohaghlab.blooddonationgallery.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

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
        holder.statusRecy.setText(user.getStatus());
        holder.dateTitleRecy.setText(user.getDatetitle());
        Glide.with(context).load(user.getProfileimageurl()).error(R.drawable.user).into(holder.profileImageRecy);

        ////





        /////

        holder.callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNo = user.getPhone();
                String callnow ="tel:"+phoneNo.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(callnow));
                context.startActivity(intent);





            }
        });





        ///add out

        holder.shareNowRecy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NamE=user.getName();
                String PhonE=user.getPhone();
                String bloogGroup=user.getBloodgroup();
                String status=user.getStatus();
                String ciTy=user.getCity();
                String tyPe=user.getType();

                String playlink="https://play.google.com/store/apps/details?id=com.sohaghlab.blooddonationgallery";

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Assalaamualaikum wa rahmatullaah "+"."+ " Myself " +NamE +"." + " I am from "+ciTy+"."
               + " My blood group " + bloogGroup +"."+" I am a "+tyPe +" My BDG status is " +status +"\nPlease contract  \n"+PhonE+"."+" Allah bless you"+"."+ "\n\nApplication Download Link: \n"+playlink );
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);





            }
        });














    }

    @Override
    public int getItemCount() {
        return userList.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView profileImageRecy;
        TextView nameRecy,phoneRecy,cityRecy,ageRecy,lastDonationRecy,bloodRecy,typeRecy;
        ImageView callNow,shareNowRecy;
        TextView statusRecy,dateTitleRecy;

        TemplateView template;
        private InterstitialAd mInterstitialAd;








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
            statusRecy=itemView.findViewById(R.id.statusId_card);
            dateTitleRecy=itemView.findViewById(R.id.donateDateFix);

            callNow=itemView.findViewById(R.id.callNowRecy);
            shareNowRecy=itemView.findViewById(R.id.shareNowRecy);


            MobileAds.initialize(context);






        }
    }
}
