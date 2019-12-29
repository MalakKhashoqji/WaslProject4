package com.company_name.wasl_project4;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.company_name.wasl_project4.activity.UserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class staff_student extends Fragment {
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextPhone,editTextConfirmPass ;
    private Button signUpButton ;
    DatabaseReference myref;
    private FirebaseAuth myAuth;
    private ProgressBar progressBar;


    public staff_student() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_staff_student, container, false);

        editTextUsername =(EditText)v.findViewById(R.id.username_edit_text2) ;
        editTextEmail =(EditText)v.findViewById(R.id.email_address_edit_text) ;
        editTextPhone =(EditText)v.findViewById(R.id.phone_edit_text2) ;
        editTextPassword =(EditText)v.findViewById(R.id.password_edit_text);
        editTextConfirmPass =(EditText)v.findViewById(R.id.confirm_password_edit_text2);
        progressBar =(ProgressBar) v.findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);



        FirebaseApp.initializeApp(getContext());
        myAuth=FirebaseAuth.getInstance();


        signUpButton = (Button)v.findViewById(R.id.signup_button2) ;

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();





                if (username.isEmpty()) {
                    editTextUsername.setError(getString(R.string.input_error_name));
                    editTextUsername.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    editTextEmail.setError(getString(R.string.input_error_email));
                    editTextEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError(getString(R.string.input_error_email_invalid));
                    editTextEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError(getString(R.string.input_error_password));
                    editTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editTextPassword.setError(getString(R.string.input_error_password_length));
                    editTextPassword.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    editTextPhone.setError(getString(R.string.input_error_phone));
                    editTextPhone.requestFocus();
                    return;
                }

                if (phone.length() != 10) {
                    editTextPhone.setError(getString(R.string.input_error_phone_invalid));
                    editTextPhone.requestFocus();
                    return;
                }

                register(username,phone,password,email);

            }
        });


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(myAuth.getCurrentUser() != null ){
            //handle the already login user//DO IT LATER OMAMAH//
        }
    }


    public void register(String username, String phone, String password , String email) {
        progressBar.setVisibility(View.VISIBLE);
        myAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            myAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                            UserClass user = new UserClass( username, email, phone, password, "StudentStaff");
                            Log.d("USer:", "Regesterd Successfully with "+user.getType());
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "User Register successfully", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getContext(), "User Does not Register", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
    }
