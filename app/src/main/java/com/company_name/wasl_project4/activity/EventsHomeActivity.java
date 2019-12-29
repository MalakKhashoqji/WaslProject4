/**
 *  Created by waslTeam.
 */

package com.company_name.wasl_project4.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.company_name.wasl_project4.R;
import com.company_name.wasl_project4.databinding.EventsHomeActivityBinding;


public class EventsHomeActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, EventsHomeActivity.class);
	}
	
	private EventsHomeActivityBinding binding;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.events_home_activity);
	}
	
	public void setupToolbar() {
	
		setSupportActionBar(binding.toolbar);
		
		// Additional Toolbar setup code can go here.
	}
	
	public void onAttendingTwoPressed() {
	
	}
	
	public void onAttendingPressed() {
	
	}
	
	public void onUpcomingPressed() {
	
	}
}
