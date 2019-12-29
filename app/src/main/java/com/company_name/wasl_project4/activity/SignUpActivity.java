/**
 *  Created by waslTeam.
 */

package com.company_name.wasl_project4.activity;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.company_name.wasl_project4.R;
import com.company_name.wasl_project4.adapter.viewPagerAdapter;
import com.company_name.wasl_project4.databinding.SignUpActivityBinding;
import com.company_name.wasl_project4.organizer;
import com.company_name.wasl_project4.staff_student;


public class SignUpActivity extends AppCompatActivity {
	Toolbar signUp_toolbar ;
	TabLayout taplayout;
	ViewPager viewPager;
	viewPagerAdapter viewPagerAdapter;



	private EditText editTextUsername, editTextEmail, editTextPassword, editTextPhone,editTextName ;
	private Button signUpButton ;

	private SignUpActivityBinding binding;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.sign_up_activity);


		signUp_toolbar=(Toolbar) findViewById(R.id.signUp_toolBar) ;
		setSupportActionBar(signUp_toolbar);
		taplayout =(TabLayout)findViewById(R.id.tabLayout);
		viewPager =(ViewPager) findViewById(R.id.view_pager);
		viewPagerAdapter =new viewPagerAdapter(getSupportFragmentManager());
		viewPagerAdapter.addfragments(new organizer(),"Organizer");
        viewPagerAdapter.addfragments(new staff_student(),"Student or Staff");
        viewPager.setAdapter(viewPagerAdapter);
        taplayout.setupWithViewPager(viewPager);






		FragmentManager manager =getSupportFragmentManager();
		FragmentTransaction transaction= manager.beginTransaction();
		organizer  orgnizerFragment = new organizer();


		//myref = FirebaseDatabase.getInstance().getReference().child("User");


		this.init();
	}




    public static Intent newIntent(Context context) {

        // Fill the created intent with the data you want to be passed to this Activity when it's opened.
        return new Intent(context, SignUpActivity.class);
    }


    private void init() {
		// Configure Buttons component

	}

	public void onButtonsPressed() {

		startAnimationOne();
		startAnimationTwo();
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

		Keyframe kf7 = Keyframe.ofFloat(0.0f, 0f);
		Keyframe kf8 = Keyframe.ofFloat(0.6f, 1f);
		Keyframe kf9 = Keyframe.ofFloat(1.0f, 1f);

		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf7, kf8, kf9);

		AnimatorSet set1 = new AnimatorSet();

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


		Keyframe kf7 = Keyframe.ofFloat(0.0f, 0f);
		Keyframe kf8 = Keyframe.ofFloat(0.6f, 1f);
		Keyframe kf9 = Keyframe.ofFloat(1.0f, 1f);

		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofKeyframe(View.ALPHA, kf7, kf8, kf9);

		AnimatorSet set1 = new AnimatorSet();
		AnimatorSet set2 = new AnimatorSet();
		set2.playTogether(set1);
		set2.start();
	}
}
