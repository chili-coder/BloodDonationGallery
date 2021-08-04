package com.sohaghlab.blooddonationgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sohaghlab.blooddonationgallery.Model.User;

public class LoginActivity extends AppCompatActivity {

    TextView signup, forgotPass;
    private TextInputEditText emailLog,passwordLog;
    private ProgressDialog loaderDiaglog;
    Button signIn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup=findViewById(R.id.backtoSignIn);
        signIn=findViewById(R.id.sendEmailVery);
        emailLog=findViewById(R.id.email_verification);
        passwordLog=findViewById(R.id.password_login);
        forgotPass=findViewById(R.id.forgot_signin_password);



        loaderDiaglog=new ProgressDialog(this);


        mAuth=FirebaseAuth.getInstance();
        authStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                   Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    // User is not logged in
                  // startActivity(new Intent(this,LoginActivity.class));
                   //Toast.makeText(LoginActivity.this, "Please SignIn Agien", Toast.LENGTH_SHORT).show();
                }

            }
        };

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
               startActivity(intent);


            }
        });



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = emailLog.getText().toString().trim();
                final String password = passwordLog.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    emailLog.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passwordLog.setError("Password is required!");
                    return;
                }
                else {
                    loaderDiaglog.setMessage("SignIn Loading...");
                    loaderDiaglog.setCanceledOnTouchOutside(false);
                    loaderDiaglog.show();

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                                Toast.makeText(LoginActivity.this, "SignIn Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("SignIn Error!")
                                        .setMessage("Your email & password incorrect")
                                        .setPositiveButton("ok", null)
                                        .show();
                            }

                            loaderDiaglog.dismiss();
                        }
                    });

                   }


                }
        });







        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SelectRegiActivity.class);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(authStateListener);
    }
}