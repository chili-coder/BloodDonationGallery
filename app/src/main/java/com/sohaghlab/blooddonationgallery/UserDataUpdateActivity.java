package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sohaghlab.blooddonationgallery.Model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDataUpdateActivity extends AppCompatActivity {

   private CircleImageView updateImage;
   private EditText nameUpdate,phoneUpdate,cityUpdate,userIdUpdate,ageUpdate,lastDoneteUpdate;
  private   Button updateBtn;
    private Uri resultUri;

    private FirebaseAuth mAuth;
    private DatabaseReference userDataRef;
    private TextView backtoprofile;
    private Spinner status_spnner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_update);

        updateImage=findViewById(R.id.update_image);
       ageUpdate=findViewById(R.id.update_age);
       nameUpdate=findViewById(R.id.update_name);
        phoneUpdate=findViewById(R.id.update_phone);
        cityUpdate=findViewById(R.id.update_city);
        userIdUpdate=findViewById(R.id.update_nid);
        lastDoneteUpdate=findViewById(R.id.update_last_donete_date);
        updateBtn=findViewById(R.id.update_btn);
        status_spnner=findViewById(R.id.status_spnner);





        mAuth=FirebaseAuth.getInstance();

       /* String _name,_age,_phone,_city,_lastdonete,_uerId;
        Intent intent = getIntent();
        _name=intent.getStringExtra("name");
        _phone=intent.getStringExtra("phone");
        _city=intent.getStringExtra("city");
        _age=intent.getStringExtra("age");
        _uerId=intent.getStringExtra("userid");
        _lastdonete=intent.getStringExtra("lastdonation");

        nameUpdate.setText(_name);
        phoneUpdate.setText(_phone);
        ageUpdate.setText(_age);
        cityUpdate.setText(_city);
        lastDoneteUpdate.setText(_lastdonete);

        */



        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UserDataUpdateActivity.this)
                        .crop()
                        .cropSquare()
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        backtoprofile=findViewById(R.id.backtoprofile);
        backtoprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDataUpdateActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                   nameUpdate.setText(snapshot.child("name").getValue().toString());
                   phoneUpdate.setText(snapshot.child("phone").getValue().toString());
                   ageUpdate.setText(snapshot.child("age").getValue().toString());
                   cityUpdate.setText(snapshot.child("city").getValue().toString());
                  userIdUpdate.setText(snapshot.child("userid").getValue().toString());

                   lastDoneteUpdate.setText(snapshot.child("lastdonation").getValue().toString());



                    if (snapshot.hasChild("profileimageurl")){

                        Glide.with(getApplicationContext()).load(snapshot.child("profileimageurl")
                                .getValue().toString()).into(updateImage);

                    } else {
                        updateImage.setImageResource(R.drawable.user);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String name = nameUpdate.getText().toString().trim();
                final String userId = userIdUpdate.getText().toString().trim();
                final String city = cityUpdate.getText().toString().trim();
                final String phone = phoneUpdate.getText().toString().trim();
                final String age = ageUpdate.getText().toString().trim();
                final String lastdonation = lastDoneteUpdate.getText().toString().trim();
                final String status = status_spnner.getSelectedItem().toString();




                String currentUserId = mAuth.getCurrentUser().getUid();
                userDataRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

              userDataRef.child("users").child(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<DataSnapshot> task) {

                      if (!task.isSuccessful()){
                          Log.e("firebase", "Error getting data", task.getException());
                      }else {

                          String currentUserId = mAuth.getCurrentUser().getUid();
                          userDataRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

                          HashMap userInfo = new HashMap();
                          userInfo.put("name",name);
                          userInfo.put("city",city);
                          userInfo.put("phone",phone);
                          userInfo.put("userid",userId);
                          userInfo.put("age",age);
                          userInfo.put("lastdonation",lastdonation);
                          userInfo.put("status",status);




                          userDataRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                              @Override
                              public void onComplete(@NonNull Task task) {

                                  if (task.isSuccessful()){


                                  }else {

                                      new AlertDialog.Builder(UserDataUpdateActivity.this)
                                              .setTitle("Updating Error!")
                                              .setMessage("Please try agian")
                                              .setPositiveButton("ok", null)
                                              .show();
                                      // Toast.makeText(DonnerRegiActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                  }

                                  finish();
                                  // loaderDiaglog.dismiss();

                              }
                          });

                          if (resultUri !=null){
                              final StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile image").child(currentUserId);
                              Bitmap bitMap =null;
                              try {
                                  bitMap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
                              }catch (IOException e){
                                  e.printStackTrace();
                              }

                              ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                              bitMap.compress(Bitmap.CompressFormat.JPEG, 20,byteArrayOutputStream);
                              byte[] data = byteArrayOutputStream.toByteArray();
                              UploadTask uploadTask =filePath.putBytes(data);

                              uploadTask.addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {

                                      Toast.makeText(UserDataUpdateActivity.this, "Image Upload Failed!", Toast.LENGTH_SHORT).show();

                                  }
                              });

                              uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                  @Override
                                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                      if (taskSnapshot.getMetadata() !=null && taskSnapshot.getMetadata().getReference() !=null){

                                          Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                          result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                              @Override
                                              public void onSuccess(Uri uri) {
                                                  String imageUri =uri.toString();
                                                  Map newImageMap = new HashMap();
                                                  newImageMap.put("profileimageurl",imageUri);

                                                  userDataRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                      @Override
                                                      public void onComplete(@NonNull Task task) {
                                                          if (task.isSuccessful()){
                                                              Toast.makeText(UserDataUpdateActivity.this, "", Toast.LENGTH_SHORT).show();

                                                          } else {
                                                              Toast.makeText(UserDataUpdateActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                                                          }
                                                      }
                                                  });

                                                  finish();

                                              }
                                          });


                                      }
                                  }
                              });

                              Intent intent = new Intent(UserDataUpdateActivity.this,ProfileActivity.class);
                              startActivity(intent);
                              finish();



                          }



                      }
                  }
              });










            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        resultUri= data.getData();
        updateImage.setImageURI(resultUri);


    }



}
