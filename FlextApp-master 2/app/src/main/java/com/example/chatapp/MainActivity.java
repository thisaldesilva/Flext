package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Fragments.ChatsFragment;
import com.example.chatapp.Fragments.ProfileFragment;
import com.example.chatapp.Fragments.UsersFragment;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    AES aes = new AES();



    //declare a variable to hold the profile picture
    CircleImageView profile_image;

    //declare a variable hold the user name so that it can be displayed on the top tool bar
    TextView username;

    //declare variable hold firebase user and database reference
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //call the super call method so that we can user fragments
        super.onCreate(savedInstanceState);

        //link the UI to this activity
        setContentView(R.layout.activity_main);

        //make the required things to the top tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        //link image position in the UI
        profile_image = findViewById(R.id.profile_image);
        //link username segment in the UI
        username = findViewById(R.id.username);

        //get the firebase instance
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //get the reference to firebase database user instance so that later on we can get status, image etc.
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        //if there is change in the particulars users database reference
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // store the required user details
                User user = dataSnapshot.getValue(User.class);

                // displaying the username on the appbar layout
                username.setText(user.getUsername());

                // checking if the user has uploaded a profile image, if not displaying the default image
                if (user.getImageURL().equals("default"))
                {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }
                else
                {
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //link the top view bar and bottom fragment sections
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);

        //make a link to the chats on the firebase
        reference = FirebaseDatabase.getInstance().getReference("Chats");

        //in the case of update to the chats get the data  into the application
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //making the fragment sections
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

                //for the number of unread messages
                int unread = 0;

                //check for required messages
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    //take data into chat format
                    Chat chat = snapshot.getValue(Chat.class);

                    //check for my messages and then check if the message is unseen
                    //if so then count  the message as an unread one
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && !chat.isIsseen())
                    {
                        unread++;
                    }
                }

                //if there is no unseen messages then just display chats
                //otherwise display chats with the number of unseen number on the front
                if (unread == 0)
                {
                    //create the fragment
                    viewPagerAdapter.addFragment(new ChatsFragment(), getString(R.string.chats));
                }
                else
                {
                    viewPagerAdapter.addFragment(new ChatsFragment(), "("+unread+") " + getString(R.string.chats));
                }

                //add other two fragments to the scene
                viewPagerAdapter.addFragment(new UsersFragment(), getString(R.string.users));
                viewPagerAdapter.addFragment(new ProfileFragment(), getString(R.string.profile));

                //display the bottom part to the UI
                viewPager.setAdapter(viewPagerAdapter);

                //display the top part of the UI
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //this function will show the 3dot drop down menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //link to the  UI fragment
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //this function will excecute the  selected function
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

                Log.e("Im here", "here here");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                Log.e("Im here", "here here after");
                startActivity(new Intent(MainActivity.this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                return true;
        }

        return false;
    }

    //class for the viewadapter
    //where the fragments are handled
    //this inherites from android classes
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    //this method updates our status to the firebase
    private void status(String status)
    {
        //make link to user account on firebase
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        //update the status on the user's box
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        //update the data
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
