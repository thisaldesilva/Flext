package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    //declare variables to hold text fields on the UI
    MaterialEditText email, password;

    //declare variables to hold buttons on the UI
    Button btn_login;
    TextView forgot_password;

    //declare a variable to hold authentication instance
    FirebaseAuth auth;

    //this function will get called by the UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //when this method get called goto this particular view
        setContentView(R.layout.activity_login);

        //load the universal tool bar into this view
        Toolbar toolbar = findViewById(R.id.toolbar);

        //set the tool bar name and make the required changes
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get firebase authentication instance
        auth = FirebaseAuth.getInstance();

        //link the UI constants to the login activity
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);

        //in the case of user pressing forgot password button
        //direct the user to reset password page
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        email.setHintTextColor(Color.GRAY);
        password.setHintTextColor(Color.GRAY);
        email.setUnderlineColor(Color.GRAY);
        password.setUnderlineColor(Color.GRAY);
        email.setTextColor(Color.GRAY);
        password.setTextColor(Color.GRAY);

        //listener for the login button
        //this will get called when the user clicks on login button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get user typed strings on the UI into variables  (Email & Password)
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                //first validate the  information passed
                //check if all fields contains text
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password))
                {
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                //if all fields are provided then proceed further
                else
                {
                    //call the firebase authentication to login the user
                    //this method will return true if the correct credentials are given otherwise false
                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        //if the correct login details are given and the user is logged-in the transfer to main_activity
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        //if the credentials are wrong then stay on the same page and display an error message
                                        Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
