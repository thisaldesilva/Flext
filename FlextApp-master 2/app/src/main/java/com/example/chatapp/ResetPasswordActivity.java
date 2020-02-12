package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    //create variables to hold user's email address and button
    EditText send_email;
    Button btn_reset;

    //create a variable to hold firebase authentication
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //link the reset view
        setContentView(R.layout.activity_reset_password);

        //load the toolbar  and make necessary changes
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.resetPassword));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //link the UI elements
        send_email = findViewById(R.id.send_email);
        btn_reset = findViewById(R.id.btn_reset);

        send_email.setHintTextColor(Color.GRAY);
        send_email.setTextColor(Color.GRAY);

        //connect  to the firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //listener for the reset password button
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the user typed email into a variable
                String email = send_email.getText().toString();

                //check if user has typed an email address (if  not promt the user to type a valid email address)
                if (email.equals(""))
                {
                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.enterValidEmail), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //if the is a valid email address then proceed further
                    //send the resetpassword call to firebase
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //if everything goes well (if correct email is found on the firebase) then display the relevant message
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ResetPasswordActivity.this, getString(R.string.pleasecheckyourInbox), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            }
                            else
                            {
                                //otherwise display the problem to the user
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
