package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    //declare two variable to hold the two buttons on the  first page
    Button login, register;

    //declare variable to hold the firebase user object
    FirebaseUser firebaseUser;

    //this function will get called on the start of the application
    @Override
    protected void onStart() {
        super.onStart();

        //call the firebase and get the instance
        //firebase will return null uf the user is not logged-in
        //otherwise  it will return the instance  to  the  caller  and store it on the firebaseUser

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Log.e("user", "half-way here in the on start");

        //Log.e("user", firebaseUser.getUid());
        //check if user is not null which  means that the user is logged-in then
        //direct  the user to the main-activity where the homepage will be shown
        //otherwise stay on the start activity and wait for a button clicked for either  logg-in or register
        if (firebaseUser ==  null)
        {
            Log.e("Firebase-User", "Null");

        }
        else
        {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    //UI will Automatically call this function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //create link to the buttons  on XML file
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        Log.e("user", "half-way here!!!");


        //Log.e("user", firebaseUser.getUid());

        //button listner for the log-in
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        //button listner  for  the register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });
    }
}
