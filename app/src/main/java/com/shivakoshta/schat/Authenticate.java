package com.shivakoshta.schat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shivakoshta.schat.databinding.ActivityAuthenticateBinding;
import com.shivakoshta.schat.databinding.ActivityMainBinding;

public class Authenticate extends AppCompatActivity {

    ActivityAuthenticateBinding binding;
    String name, email , password;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        binding.Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = binding.EmailLogin.getText().toString();
                password = binding.PasswordLogin.getText().toString();
//                Toast.makeText(Authenticate.this, name, Toast.LENGTH_SHORT).show();
                if(email.length()==0)
                {
                    Toast.makeText(Authenticate.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6)
                {
                    if(password.length()==0)
                        Toast.makeText(Authenticate.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Authenticate.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else
                login();
            }

        });

        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.NameLogin.getText().toString();
                email = binding.EmailLogin.getText().toString();
                password = binding.PasswordLogin.getText().toString();

                if(name.length()==0)
                {
                    Toast.makeText(Authenticate.this, "Invalid Name", Toast.LENGTH_SHORT).show();
                }
                else if(email.length()==0)
                {
                    Toast.makeText(Authenticate.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6)
                {
                    if(password.length()==0)
                    Toast.makeText(Authenticate.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Authenticate.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else
                signUp();
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            startActivity(new Intent(Authenticate.this,MainActivity.class));
            finish();
        }
    }

    private void login() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(Authenticate.this,MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Authenticate.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void signUp() {

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        
                        UserProfileChangeRequest userProfileChangeRequest = new  UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);
                        UserModel userModel = new UserModel(FirebaseAuth.getInstance().getUid(),name,email,password);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
                        startActivity(new Intent(Authenticate.this,MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Authenticate.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}