package com.company_name.wasl_project4.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company_name.wasl_project4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class editOrganizerProfile extends HomePage {


    private static final int CHOOSE_IMAGE = 101;

    ImageView image,profileImage;
    EditText displayNameEditText , emailEditText , phoneEditText, deanshipEditText;
    Button saveDataBtn;

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
        getLayoutInflater().inflate(R.layout.activity_edit_organizer_profile, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);



        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID =user.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Users").child(userID);



        image = (ImageView) findViewById(R.id.selectPhotoBtn);
        profileImage=(ImageView) findViewById(R.id.profileImg);
        saveDataBtn=(Button) findViewById(R.id.saveBtn) ;
        emailEditText =(EditText)findViewById(R.id.emailEditText);
        phoneEditText =(EditText)findViewById(R.id.phoneEditText);
        displayNameEditText=(EditText)findViewById(R.id.nameEditText) ;
        deanshipEditText =(EditText)findViewById(R.id.deanshipEditText);
        progressBar=(ProgressBar) findViewById(R.id.progressBar4);



        Log.d(TAG, "showData: USER ID IS : " + userID);
        Log.d(TAG, "showData: MyRef  IS : " + myRef);





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String deanship = dataSnapshot.child("deanship").getValue().toString();
                String phone_number = dataSnapshot.child("phone_number").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                userType=dataSnapshot.child("type").getValue().toString();

                emailEditText.setText(email);
                phoneEditText.setText(phone_number);
                deanshipEditText.setText(deanship);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });



        loadUserInformation();

        saveDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();

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
                displayNameEditText.setText(user.getDisplayName());
            }

        }
    }


  /*  @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        if (mAuth.getCurrentUser() == null) {
            //if the user is not logged in
            finish();
            startActivity(new Intent(this, SignInActivity.class));//let him go to the login page
        }
    }*/

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }




    private void saveUserInformation() {

        String displayName = displayNameEditText.getText().toString().trim();
        String email =emailEditText.getText().toString().trim();
        String phone =phoneEditText.getText().toString().trim();
        String deanship=deanshipEditText.getText().toString().trim();


        if (displayName.isEmpty()) {
            displayNameEditText.setError("Name required");
            displayNameEditText.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailEditText.setError(getString(R.string.input_error_email));
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(getString(R.string.input_error_email_invalid));
            emailEditText.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            phoneEditText.setError(getString(R.string.input_error_phone));
            phoneEditText.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            phoneEditText.setError(getString(R.string.input_error_phone_invalid));
            phoneEditText.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();


            user.updateEmail(email);
            updateUserInfo(new UserClass(),deanship,displayName,phone,email,profileImageUrl );
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(editOrganizerProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                profileImage.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);


                            profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    profileImageUrl = uri.toString();
                                    Toast.makeText(getApplicationContext(), "Image Upload Successful", Toast.LENGTH_SHORT).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
    public void updateUserInfo( UserClass user, String deanship , String DisplayName, String phone_number, String email, String imgURL){

        user.setDeanship(deanship);
        user.setPhone_number(phone_number);
        user.setName(DisplayName);
        user.setEmail(email);
        user.setType(userType);
        user.setImgUrl(imgURL);
        myRef.setValue(user);


    }

    private void showImageChooser (){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }

}
