/**
 *  Created by waslTeam.
 */

package com.company_name.wasl_project4.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.company_name.wasl_project4.R;
import com.company_name.wasl_project4.databinding.SignInActivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.supernova.uitoolkit.drawable.LinearGradientDrawable;


public class SignInActivity extends AppCompatActivity {
	Button signInBtn;
	FirebaseAuth myAuth;
	EditText emailEditText, passEditText;
	private ProgressBar progressBar;
	private FirebaseAuth.AuthStateListener mAuthListener;
	private DatabaseReference myRef;
	FirebaseUser user;
	private  String userID;
	private FirebaseDatabase mFirebaseDatabase;
	private ValueEventListener mDBListener;

	private static final String TAG = "ViewDatabase";


	String userType;

	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, SignInActivity.class);
	}
	
	private SignInActivityBinding binding;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.sign_in_activity);
		this.init();




		signInBtn=(Button)findViewById(R.id.signin_button);
		myAuth=FirebaseAuth.getInstance();
		emailEditText=(EditText)findViewById(R.id.username_id_edit_text) ;
		passEditText=(EditText)findViewById(R.id.password_edit_text) ;
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);

		signInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userLogin();
			}
		});


	}

	private void userLogin(){
		String email= emailEditText.getText().toString();
		String password= passEditText.getText().toString();



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

		if (password.isEmpty()) {
			passEditText.setError(getString(R.string.input_error_password));
			passEditText.requestFocus();
			return;
		}

		if (password.length() < 6) {
			passEditText.setError(getString(R.string.input_error_password_length));
			passEditText.requestFocus();
			return;
		}

		progressBar.setVisibility(View.VISIBLE);

		myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@SuppressLint("RestrictedApi")
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if(task.isSuccessful()){
					user = myAuth.getCurrentUser();
					userID =user.getUid();
					mFirebaseDatabase = FirebaseDatabase.getInstance();
					myRef = mFirebaseDatabase.getReference().child("Users").child(userID);


					Log.d(TAG, "showData: USER ID IS : " + userID);
					Log.d(TAG, "showData: MyRef  IS : " + myRef);

					ValueEventListener valueEventListener = new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							if(dataSnapshot.child("type").getValue().toString().equals( "Organizer") ){
								Log.d("DD","USER TYPE:" +dataSnapshot.child("type").getValue().toString());
								Intent i=new Intent(getApplicationContext(),homeEvent.class);
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //TO CLEAR All the activities in the top of the stack and open new activity
								//this flag so the user can not back to login and register activities
								startActivity(i);
							} else {
								Log.d("DD","USER TYPE:" +dataSnapshot.child("type").getValue().toString());
								Intent i=new Intent(getApplicationContext(),studet_staff_homePage.class);
								startActivity(i);
							}
						}

						@Override
						public void onCancelled(DatabaseError databaseError) {

						}
					};

					myRef.addListenerForSingleValueEvent(valueEventListener);

					progressBar.setVisibility(View.GONE);
					}

				else {
				progressBar.setVisibility(View.GONE);
					Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
				}
			}

		});

	}

	/*@Override
	protected void onStart() {
		super.onStart();
		if(myAuth.getCurrentUser() != null){
			finish();
			Intent i=new Intent(getApplicationContext(),editOrganizerProfile.class);

	}
	}*/

	private void init() {

		// Configure Sign in error component
		binding.signInErrorConstraintLayout.setBackground(new LinearGradientDrawable.Builder(this, new PointF(0.31f, 1.1f), new PointF(0.69f, -0.1f)).addStop(0f, Color.argb(255, 29, 146, 130)).addStop(1f, Color.argb(255, 38, 225, 200)).build());

		// Configure Forgot password? component
		binding.forgotPasswordButton.setOnClickListener((view) -> {
	this.onForgotPasswordPressed();
});

		// Configure SignIN component
		binding.signinButton.setOnClickListener((view) -> {
	this.onButtonsPressed();
});

		// Configure ico-close-modal component
		binding.icoCloseModalButton.setOnClickListener((view) -> {
	this.onIcoCloseModalPressed();
});
	}
	
	public void onForgotPasswordPressed() {
	
		startAnimationOne();
	}

	public void onButtonsPressed() {

		startAnimationTwo();
		startAnimationThree();
		startAnimationFour();
		this.startEventsHomeActivity();
	}
	
	public void onIcoCloseModalPressed() {
	
		this.startRegisterTwoActivity();
	}
	
	private void startRegisterTwoActivity() {
	
		this.startActivity(RegisterTwoActivity.newIntent(this));
	}

	private void startEventsHomeActivity() {

		this.startActivity(EventsHomeActivity.newIntent(this));
	}
	
	public void startAnimationOne() {
	
		Keyframe kf1 = Keyframe.ofFloat(0.0f, 0.3f);
		Keyframe kf2 = Keyframe.ofFloat(0.2f, 1.1f);
		Keyframe kf3 = Keyframe.ofFloat(0.4f, 0.9f);
		Keyframe kf4 = Keyframe.ofFloat(0.6f, 1.03f);
		Keyframe kf5 = Keyframe.ofFloat(0.8f, 0.97f);
		Keyframe kf6 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh1 = PropertyValuesHolder.ofKeyframe(View.SCALE_X, kf1, kf2, kf3, kf4, kf5, kf6);
		PropertyValuesHolder pvh2 = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, kf1, kf2, kf3, kf4, kf5, kf6);
		
		Animator animator1 = ObjectAnimator.ofPropertyValuesHolder(binding.forgotPasswordButton, pvh1, pvh2);
		animator1.setDuration(1000);
		animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		Keyframe kf7 = Keyframe.ofFloat(0.0f, 0f);
		Keyframe kf8 = Keyframe.ofFloat(0.6f, 1f);
		Keyframe kf9 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf7, kf8, kf9);
		
		Animator animator2 = ObjectAnimator.ofPropertyValuesHolder(binding.forgotPasswordButton, pvh3);
		animator2.setDuration(1000);
		animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		AnimatorSet set1 = new AnimatorSet();
		set1.playTogether(animator1, animator2);
		set1.setTarget(binding.forgotPasswordButton);
		AnimatorSet set2 = new AnimatorSet();
		set2.playTogether(set1);
		set2.start();
	}
	
	public void startAnimationTwo() {
	
		Keyframe kf1 = Keyframe.ofFloat(0.0f, -3000f);
		Keyframe kf2 = Keyframe.ofFloat(0.6f, 25f);
		Keyframe kf3 = Keyframe.ofFloat(0.75f, -10f);
		Keyframe kf4 = Keyframe.ofFloat(0.9f, 5f);
		Keyframe kf5 = Keyframe.ofFloat(1.0f, 0f);
		
		PropertyValuesHolder pvh1 = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_Y, kf1, kf2, kf3, kf4, kf5);
		
		Animator animator1 = ObjectAnimator.ofPropertyValuesHolder(binding.signinButton, pvh1);
		animator1.setDuration(1000);
		animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.61f, 1f));
		
		Keyframe kf6 = Keyframe.ofFloat(0.0f, 0f);
		Keyframe kf7 = Keyframe.ofFloat(0.6f, 1f);
		Keyframe kf8 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh2 = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf6, kf7, kf8);
		
		Animator animator2 = ObjectAnimator.ofPropertyValuesHolder(binding.signinButton, pvh2);
		animator2.setDuration(1000);
		animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.61f, 1f));
		
		AnimatorSet set1 = new AnimatorSet();
		set1.playTogether(animator1, animator2);
		set1.setTarget(binding.signinButton);
		AnimatorSet set2 = new AnimatorSet();
		set2.playTogether(set1);
		set2.start();
	}
	
	public void startAnimationThree() {
	
		Keyframe kf1 = Keyframe.ofFloat(0.0f, 0.3f);
		Keyframe kf2 = Keyframe.ofFloat(0.2f, 1.1f);
		Keyframe kf3 = Keyframe.ofFloat(0.4f, 0.9f);
		Keyframe kf4 = Keyframe.ofFloat(0.6f, 1.03f);
		Keyframe kf5 = Keyframe.ofFloat(0.8f, 0.97f);
		Keyframe kf6 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh1 = PropertyValuesHolder.ofKeyframe(View.SCALE_X, kf1, kf2, kf3, kf4, kf5, kf6);
		PropertyValuesHolder pvh2 = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, kf1, kf2, kf3, kf4, kf5, kf6);
		
		Animator animator1 = ObjectAnimator.ofPropertyValuesHolder(binding.signinButton, pvh1, pvh2);
		animator1.setDuration(1000);
		animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		Keyframe kf7 = Keyframe.ofFloat(0.0f, 0f);
		Keyframe kf8 = Keyframe.ofFloat(0.6f, 1f);
		Keyframe kf9 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf7, kf8, kf9);
		
		Animator animator2 = ObjectAnimator.ofPropertyValuesHolder(binding.signinButton, pvh3);
		animator2.setDuration(1000);
		animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		AnimatorSet set1 = new AnimatorSet();
		set1.playTogether(animator1, animator2);
		set1.setTarget(binding.signinButton);
		AnimatorSet set2 = new AnimatorSet();
		set2.playTogether(set1);
		set2.start();
	}
	
	public void startAnimationFour() {
	
		Keyframe kf1 = Keyframe.ofFloat(0.0f, 0.3f);
		Keyframe kf2 = Keyframe.ofFloat(0.2f, 1.1f);
		Keyframe kf3 = Keyframe.ofFloat(0.4f, 0.9f);
		Keyframe kf4 = Keyframe.ofFloat(0.6f, 1.03f);
		Keyframe kf5 = Keyframe.ofFloat(0.8f, 0.97f);
		Keyframe kf6 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh1 = PropertyValuesHolder.ofKeyframe(View.SCALE_X, kf1, kf2, kf3, kf4, kf5, kf6);
		PropertyValuesHolder pvh2 = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, kf1, kf2, kf3, kf4, kf5, kf6);
		
		Animator animator1 = ObjectAnimator.ofPropertyValuesHolder(binding.signinButton, pvh1, pvh2);
		animator1.setDuration(1000);
		animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		Keyframe kf7 = Keyframe.ofFloat(0.0f, 0f);
		Keyframe kf8 = Keyframe.ofFloat(0.6f, 1f);
		Keyframe kf9 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf7, kf8, kf9);
		
		Animator animator2 = ObjectAnimator.ofPropertyValuesHolder(binding.signinButton, pvh3);
		animator2.setDuration(1000);
		animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		AnimatorSet set1 = new AnimatorSet();
		set1.playTogether(animator1, animator2);
		set1.setTarget(binding.signinButton);
		AnimatorSet set2 = new AnimatorSet();
		set2.playTogether(set1);
		set2.start();
	}
}
