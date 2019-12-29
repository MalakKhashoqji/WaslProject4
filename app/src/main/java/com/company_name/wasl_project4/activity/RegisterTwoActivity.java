/**
 *  Created by waslTeam.
 */

package com.company_name.wasl_project4.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.View;

import com.company_name.wasl_project4.R;
import com.company_name.wasl_project4.databinding.RegisterTwoActivityBinding;
import com.google.firebase.FirebaseApp;

import io.supernova.uitoolkit.drawable.LinearGradientDrawable;


public class RegisterTwoActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, RegisterTwoActivity.class);
	}
	
	private RegisterTwoActivityBinding binding;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		FirebaseApp.initializeApp(getApplicationContext());
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.register_two_activity);
		this.init();
	}
	
	private void init() {
	
		// Configure Register component
		binding.registerConstraintLayout.setBackground(new LinearGradientDrawable.Builder(this, new PointF(0.31f, 1.1f), new PointF(0.69f, -0.1f)).addStop(0f, Color.argb(255, 29, 146, 130)).addStop(1f, Color.argb(255, 38, 225, 200)).build());
		
		// Configure CopyRight component
		SpannableString copyrightTextViewText = new SpannableString(this.getString(R.string.register_two_activity_copyright_text_view_text));
		binding.copyrightTextView.setText(copyrightTextViewText);

		// Configure SignUP component
		binding.signupButton.setOnClickListener((view) -> {
	this.onSignUPPressed();
});
		
		// Configure LogIn component
		binding.loginButton.setOnClickListener((view) -> {
	this.onLogInPressed();
});
		

	}
	
	public void onSignUPPressed() {
	
		startAnimationOne();
		this.startSignUpActivity();
	}
	
	public void onLogInPressed() {
	
		startAnimationTwo();
		this.startSignInActivity();
	}
	
	public void onNavValueChanged() {
	
	}
	
	private void startSignUpActivity() {
		this.startActivity(SignUpActivity.newIntent(this));
	}
	
	private void startSignInActivity() {
		this.startActivity(SignInActivity.newIntent(this));
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
		
		Animator animator1 = ObjectAnimator.ofPropertyValuesHolder(binding.signupButton, pvh1, pvh2);
		animator1.setDuration(1000);
		animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		Keyframe kf7 = Keyframe.ofFloat(0.0f, 0f);
		Keyframe kf8 = Keyframe.ofFloat(0.6f, 1f);
		Keyframe kf9 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf7, kf8, kf9);
		
		Animator animator2 = ObjectAnimator.ofPropertyValuesHolder(binding.signupButton, pvh3);
		animator2.setDuration(1000);
		animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		AnimatorSet set1 = new AnimatorSet();
		set1.playTogether(animator1, animator2);
		set1.setTarget(binding.signupButton);
		AnimatorSet set2 = new AnimatorSet();
		set2.playTogether(set1);
		set2.start();
	}
	
	public void startAnimationTwo() {
	
		Keyframe kf1 = Keyframe.ofFloat(0.0f, 0.3f);
		Keyframe kf2 = Keyframe.ofFloat(0.2f, 1.1f);
		Keyframe kf3 = Keyframe.ofFloat(0.4f, 0.9f);
		Keyframe kf4 = Keyframe.ofFloat(0.6f, 1.03f);
		Keyframe kf5 = Keyframe.ofFloat(0.8f, 0.97f);
		Keyframe kf6 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh1 = PropertyValuesHolder.ofKeyframe(View.SCALE_X, kf1, kf2, kf3, kf4, kf5, kf6);
		PropertyValuesHolder pvh2 = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, kf1, kf2, kf3, kf4, kf5, kf6);
		
		Animator animator1 = ObjectAnimator.ofPropertyValuesHolder(binding.loginButton, pvh1, pvh2);
		animator1.setDuration(1000);
		animator1.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		Keyframe kf7 = Keyframe.ofFloat(0.0f, 0f);
		Keyframe kf8 = Keyframe.ofFloat(0.6f, 1f);
		Keyframe kf9 = Keyframe.ofFloat(1.0f, 1f);
		
		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf7, kf8, kf9);
		
		Animator animator2 = ObjectAnimator.ofPropertyValuesHolder(binding.loginButton, pvh3);
		animator2.setDuration(1000);
		animator2.setInterpolator(PathInterpolatorCompat.create(0.22f, 0.61f, 0.36f, 1f));
		
		AnimatorSet set1 = new AnimatorSet();
		set1.playTogether(animator1, animator2);
		set1.setTarget(binding.loginButton);
		AnimatorSet set2 = new AnimatorSet();
		set2.playTogether(set1);
		set2.start();
	}
}
