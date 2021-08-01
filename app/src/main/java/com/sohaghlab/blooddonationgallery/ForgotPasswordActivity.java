package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

   private EditText vailedEmail;
  private   TextView backtoSignIn;
  private   Button sendEmailBtn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        vailedEmail=findViewById(R.id.email_verification);
        sendEmailBtn=findViewById(R.id.sendEmailVery);
        backtoSignIn=findViewById(R.id.backtoSignIn);
        backtoSignIn=findViewById(R.id.backtoSignIn);
        progressBar=findViewById(R.id.progressBarForgot);
        mAuth = FirebaseAuth.getInstance();

        backtoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();

            }
        });


    }
    private void resetPassword(){
        String email = vailedEmail.getText().toString().trim();
        if (email.isEmpty()){
            vailedEmail.setError("Email is required!");
            vailedEmail.requestFocus();
            return;


        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            vailedEmail.setError("Please provide valid email!");
           vailedEmail.requestFocus();
            return;


        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    new AlertDialog.Builder(ForgotPasswordActivity.this)
                            .setTitle("Successfully Send")
                            .setMessage("Chack Your Email Address")
                            .setPositiveButton("ok", null)
                            .show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    new AlertDialog.Builder(ForgotPasswordActivity.this)
                            .setTitle("Send Error!")
                            .setMessage("Please try again")
                            .setPositiveButton("ok", null)
                            .show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });


    }
}