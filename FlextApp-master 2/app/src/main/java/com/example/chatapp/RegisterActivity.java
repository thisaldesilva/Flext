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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //declare variables to hold UI constance on the Register XML page
    MaterialEditText username, email, password;
    Button btn_register;

    //declare variables to hold firebase authentication
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //set the activity register XML page as the page that loads on this view
        setContentView(R.layout.activity_register);

        //initialize the tool bar for the page
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set the tool bar text on the top of the screen to REGISTER
        getSupportActionBar().setTitle(getString(R.string.register));
        //view the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //link UI constructs
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);


        //get an instance so that we can move forward with the registration
        auth = FirebaseAuth.getInstance();

        email.setHintTextColor(Color.GRAY);
        password.setHintTextColor(Color.GRAY);
        username.setMetHintTextColor(Color.GRAY);
        email.setUnderlineColor(Color.GRAY);
        password.setUnderlineColor(Color.GRAY);
        username.setUnderlineColor(Color.GRAY);
        email.setTextColor(Color.GRAY);
        password.setTextColor(Color.GRAY);
        username.setTextColor(Color.GRAY);


        //listner for the registration button
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //store registration details from the UI text boxes into variables
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                //check if the user's input contains any empty fields .. if  so shown an error
                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password))
                {
                    Toast.makeText(RegisterActivity.this, getString(R.string.fieldsAreRequired), Toast.LENGTH_SHORT).show();
                }
                //check if user password contains at least 6 characters
                else if (txt_password.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this, getString(R.string.minPasswordLen), Toast.LENGTH_SHORT).show();
                }
                //if all goes well then register the user
                else
                {
                    register(txt_username, txt_email, txt_password);
                }
            }
        });
    }

    //this method takes the username,  email-address and password as parameters and  register  the  user on the firebase realtime DB
    private void register(final String username, String email, String password)
    {
        //register  the user on the firebase using email and password
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            //if the registration successful then get the instance of the current user
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", username.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, getString(R.string.invalidPassandUser), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
