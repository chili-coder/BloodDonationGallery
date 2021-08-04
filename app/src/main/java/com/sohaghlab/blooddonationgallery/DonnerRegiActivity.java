package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonnerRegiActivity extends AppCompatActivity {

    private CircleImageView signUpImageDonner;
    private TextInputEditText signUpName,signUpPhone, signUpId,signUpEmail,signUpCity,signUpPassword, signUpAge;
    private Spinner signupBGSpinner;
    private Button signUpButton;

    private Uri resultUri;

    private ProgressDialog loaderDiaglog;

    private FirebaseAuth mAuth;
    private DatabaseReference userDataRef;



    TextView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donner_regi);

        signUpImageDonner = findViewById(R.id.image_profile);
        signupBGSpinner =findViewById(R.id.Dooner_bloodgroupspnner);
        signUpName=findViewById(R.id.donner_name_regi);
        signUpId=findViewById(R.id.donner_userid_regi);
        signUpPhone=findViewById(R.id.donner_phone_regi);
        signUpCity=findViewById(R.id.donner_city_regi);
        signUpEmail=findViewById(R.id.donner_email_regi);
        signUpPassword=findViewById(R.id.donner_pass_regi);
        signUpButton=findViewById(R.id.donner_regi_btn);
        signUpAge=findViewById(R.id.donner_age_regi);
       loaderDiaglog=new ProgressDialog(this);


       mAuth=FirebaseAuth.getInstance();


        signUpImageDonner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(DonnerRegiActivity.this)
                        .crop()
                        .cropSquare()
                        .maxResultSize(1080, 1080)
                        .start();


            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = signUpEmail.getText().toString().trim();
                final String password = signUpPassword.getText().toString().trim();
                final String name = signUpName.getText().toString().trim();
                final String userId = signUpId.getText().toString().trim();
                final String city = signUpCity.getText().toString().trim();
                final String phone = signUpPhone.getText().toString().trim();
                final String age = signUpAge.getText().toString().trim();
                final String bloodGroup = signupBGSpinner.getSelectedItem().toString();



                if (TextUtils.isEmpty(email)){
                    signUpEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    signUpPassword.setError("Password is required!");
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    signUpName.setError("Name is required!");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    signUpPhone.setError("Phone is required!");
                    return;
                }
                if (TextUtils.isEmpty(age)){
                    signUpAge.setError("Age is required!");
                    return;
                }
                if (TextUtils.isEmpty(city)){
                    signUpCity.setError("City is required!");
                    return;
                }





                if (bloodGroup.equals("Select Blood Group")){
                    Toast.makeText(DonnerRegiActivity.this, "Select Blood Group", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    loaderDiaglog.setMessage("Register Loading...");
                    loaderDiaglog.setCanceledOnTouchOutside(false);
                    loaderDiaglog.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()){

                                String error =task.getException().toString();
                                Toast.makeText(DonnerRegiActivity.this, "Error! " +error, Toast.LENGTH_SHORT).show();
                                loaderDiaglog.dismiss();

                            }else {
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDataRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

                                HashMap userInfo = new HashMap();

                                userInfo.put("id",currentUserId);
                                userInfo.put("name",name);
                                userInfo.put("bloodgroup",bloodGroup);
                                userInfo.put("userid",userId);
                                userInfo.put("phone",phone);
                                userInfo.put("age",age);
                                userInfo.put("city",city);
                                userInfo.put("email",email);
                                userInfo.put("password",password);
                                userInfo.put("type","Donor");
                                userInfo.put("lastdonation","Null");
                                userInfo.put("search","Donor"+bloodGroup);

                                userDataRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(DonnerRegiActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                                        }else {

                                            new AlertDialog.Builder(DonnerRegiActivity.this)
                                                    .setTitle("SignUp Error!")
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

                                            Toast.makeText(DonnerRegiActivity.this, "Image Upload Failed!", Toast.LENGTH_SHORT).show();

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
                                                                 Toast.makeText(DonnerRegiActivity.this, "", Toast.LENGTH_SHORT).show();

                                                             } else {
                                                                 Toast.makeText(DonnerRegiActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                                                             }
                                                            }
                                                        });

                                                        finish();

                                                    }
                                                });


                                            }
                                        }
                                    });

                                    Intent intent = new Intent(DonnerRegiActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    loaderDiaglog.dismiss();



                                }




                            }

                        }
                    });
                }


            }
        });






        signIn=findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonnerRegiActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            resultUri= data.getData();
            signUpImageDonner.setImageURI(resultUri);


    }
}