package com.company_name.wasl_project4.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.company_name.wasl_project4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class add_event extends HomePage {

    StorageReference storageReference;
    public  static final int PICK_IMAGE_REQUEST = 1;
     Button mButtonUpload;
     TextView mTextViewShowUploads;
     EditText mEditTextFileName;
    EditText for_edt;
    EditText Adress_edt;
    EditText Time_edt;
    EditText Desc_edt;
   ImageView mImageView;
     ProgressBar mProgressBar;
String uuuname, uuuemail;
     Uri mImageUri;

     StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;


    //FOr Getting Current User Info
   FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mAuth;
  FirebaseAuth.AuthStateListener mAuthListener;
DatabaseReference myRef;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); // this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_add_event, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);


        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        for_edt = findViewById(R.id.for_edt);
        Adress_edt = findViewById(R.id.Adress_edt);
        Time_edt = findViewById(R.id.Time_edt);
        Desc_edt = findViewById(R.id.Desc_edt);

        //For Getting Current User Info
        mAuth=FirebaseAuth.getInstance();
        storageReference =  FirebaseStorage.getInstance().getReference("uploads");
        myRef = FirebaseDatabase.getInstance().getReference("Users");

        user = mAuth.getCurrentUser();
        userID =user.getUid();
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        user = mAuth.getCurrentUser();
        if(user!= null){
            uuuemail = user.getEmail();
            userID = user.getUid();


        }
        Query query = myRef.orderByChild("email").equalTo(uuuemail);


        query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                uuuname = "" + ds.child("name").getValue();
                                                uuuemail = "" + ds.child("email").getValue();


                                            }


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });






        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String CourseTitle = mEditTextFileName.getText().toString();
                String CourseDesc = Desc_edt.getText().toString();
                String CourseForWhom  = for_edt.getText().toString().trim();
                String CourseAdress = Adress_edt.getText().toString().trim();
                String CourseTime = Time_edt.getText().toString().trim();
                if(TextUtils.isEmpty(CourseTitle)){
                    Toast.makeText(add_event.this,"Enter the Course Title Please",Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(CourseDesc)){
                    Toast.makeText(add_event.this,"Enter the Course Description Please",Toast.LENGTH_SHORT).show();
                    return;

                }
                if(mImageUri == null){
                    //post without an img
                    UploadCourseData(CourseTitle,CourseDesc,CourseForWhom,CourseAdress ,CourseTime,"No_Image");
                }
                else{

                    //post with an img
                    UploadCourseData(CourseTitle,CourseDesc,CourseForWhom,CourseAdress ,CourseTime,String.valueOf(mImageUri));

                }

            }

        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();

            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });
    }//oncreate
    private void UploadCourseData(final String courseTitle, final String courseDesc,final String CourseForWhom, final String CourseAdress , final String CourseTime, final String C_Image_uri) {
        final String PostID = String.valueOf(System.currentTimeMillis());
        if (C_Image_uri !=null) {
            //post with an img

            final StorageReference fileReference =   storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    //hashMap.put(Course_picturee, uri.toString());
                                    hashMap.put("username",uuuname);
                                    hashMap.put("useremail",uuuemail);
                                    hashMap.put("Audience",CourseForWhom);
                                    hashMap.put("PostAdress",CourseAdress);
                                    hashMap.put("CourseTime",CourseTime);
                                    hashMap.put("Postid",PostID);
                                    hashMap.put("PostTitle",courseTitle);
                                    hashMap.put("PostDesc",courseDesc);
                                    hashMap.put("PostImage",uri.toString());
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("uploads");
                                    reference.child(PostID).setValue(hashMap);
                                    //reference.child(Userid).setValue(hashMap)
                                    //databaseReference.child(storageReference.child(PostID).setValue(hashMap));
                                    mImageView.setImageResource(R.drawable.chooseimg);
                                    mEditTextFileName.setText("");
                                    for_edt.setText("");
                                    Adress_edt.setText("");
                                    Time_edt.setText("");
                                    Desc_edt.setText("");


                                    Toast.makeText(add_event.this, "Upload successfully", Toast.LENGTH_LONG).show();


                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
                            Toast.makeText(add_event.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });






        }
        else{
            //post without an img
            HashMap<String, Object> hashMap = new HashMap<>();
            //hashMap.put(Course_picturee, uri.toString());
            hashMap.put("nameuser",uuuname);
            hashMap.put("emailuser",uuuemail);
            hashMap.put("Audience",CourseForWhom);
            hashMap.put("PostAdress",CourseAdress);
            hashMap.put("CourseTime",CourseTime);
            hashMap.put("Postid",PostID);
            hashMap.put("PostTitle",courseTitle);
            hashMap.put("PostDesc",courseDesc);
            hashMap.put("PostImage","NoImage");
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("uploads");
            reference.child(PostID).setValue(hashMap);
            mEditTextFileName.setText("");
            for_edt.setText("");
            Adress_edt.setText("");
            Time_edt.setText("");
            Desc_edt.setText("");
            //reference.child(Userid).setValue(hashMap)
            //databaseReference.child(storageReference.child(PostID).setValue(hashMap));


            //databaseReference.child(String.valueOf(storageReference.child(System.currentTimeMillis()+"")).set);

            //mDatabaseRef.child(uploadID).setValue(upload);
            Toast.makeText(add_event.this, "Upload successfully", Toast.LENGTH_LONG).show();


        }

    }
    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).fit().into(mImageView);
            //uploadFile();


        }

    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, myEvents.class);
        startActivity(intent);
    }







    }


