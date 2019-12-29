package com.company_name.wasl_project4.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company_name.wasl_project4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class organizerProfile extends HomePage {

    private static final int CHOOSE_IMAGE = 101;

    ImageView profileImage;
    TextView displayNameText , emailText , phoneText, deanshipText;

    Uri uriProfileImage;

    ProgressBar progressBar;
    private static final String TAG = "ViewDatabase";



    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    FirebaseUser user;
    private  String userID;


    String profileImageUrl;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); // this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_organizer_profile, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);



        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID =user.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Users").child(userID);



        profileImage=(ImageView) findViewById(R.id.profileImg);
        emailText =(TextView) findViewById(R.id.profileEmailTV);
        phoneText =(TextView) findViewById(R.id.profilePhoneTextView);
        displayNameText=(TextView) findViewById(R.id.profileNameTextView) ;
        deanshipText =(TextView)findViewById(R.id.departmentProfileTextView);
        progressBar=(ProgressBar) findViewById(R.id.progressBar4);

        loadUserInformation();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String deanship = dataSnapshot.child("deanship").getValue().toString();
                String phone_number = dataSnapshot.child("phone_number").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                userType=dataSnapshot.child("type").getValue().toString();

                displayNameText.setText(name);
                emailText.setText(email);
                phoneText.setText(phone_number);
                deanshipText.setText(deanship);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {

                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(profileImage);
            }

            else

            if (user.getDisplayName() != null) {
                displayNameText.setText(user.getDisplayName());
            }

        }
    }




    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }




}
