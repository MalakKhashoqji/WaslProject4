package com.company_name.wasl_project4;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.company_name.wasl_project4.activity.UserClass;
import com.company_name.wasl_project4.activity.editOrganizerProfile;
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
public class organizer extends Fragment {
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextPhone,editTextName ;
    private Button signUpButton ;
    private Spinner mySpinner;
    DatabaseReference myref;
    private FirebaseAuth myAuth;
    private ProgressBar progressBar;




    public organizer() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_organizer, container, false);
        editTextUsername =(EditText)v.findViewById(R.id.username_orgnizer_edit_text) ;
        editTextEmail =(EditText)v.findViewById(R.id.email_address_orgnizer_edit_text) ;
        editTextPhone =(EditText)v.findViewById(R.id.orgnizer_phone_edit_text2) ;
        editTextName =(EditText)v.findViewById(R.id.name_orgnizer_edit_text) ;
        editTextPassword =(EditText)v.findViewById(R.id.password_orgnizer_edit_text);

        progressBar = v.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        FirebaseApp.initializeApp(getContext());
        myAuth=FirebaseAuth.getInstance();


        mySpinner=(Spinner) v.findViewById(R.id.departmentSpinner);



        //set the departments and deanships from string values to adapter then to spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1
                , getResources().getStringArray(R.array.departments));

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(spinnerAdapter);



        signUpButton = (Button)v.findViewById(R.id.signup_button) ;

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String deanship = mySpinner.getSelectedItem().toString();




                if (name.isEmpty()) {
                    editTextName.setError(getString(R.string.input_error_name));
                    editTextName.requestFocus();
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

                register(username,phone,name,password,deanship,email);
            }
        });

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        /*if(myAuth.getCurrentUser() != null ){
            getActivity().finish();//handle the already logged in user
            Intent i=new Intent(getContext(),editOrganizerProfile.class);
        }*/
    }


    public void register(String username, String phone, String name, String password , String deanship, String email){
        progressBar.setVisibility(View.VISIBLE);
        myAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()) {

                progressBar.setVisibility(View.GONE);
                myAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                UserClass user=new UserClass(name,username,email, phone, password , deanship,"Organizer");
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            getActivity().finish();
                            Intent i=new Intent(getContext(), editOrganizerProfile.class);
                            Toast.makeText(getContext(), "User Register successfully", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext(), "User Does not Register ", Toast.LENGTH_LONG).show();
                         }
                    }
                });

            }
            else{
                progressBar.setVisibility(View.GONE);
                Log.e("ERROR:",task.getException().getMessage());
                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

            }
            }
        });



    }

}
