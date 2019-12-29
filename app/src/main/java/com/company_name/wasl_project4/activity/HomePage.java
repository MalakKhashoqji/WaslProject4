package com.company_name.wasl_project4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.company_name.wasl_project4.R;
import com.google.firebase.auth.FirebaseAuth;


public class HomePage extends AppCompatActivity  {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        final String appPackageName = getPackageName();

                        switch (item.getItemId()) {

                            case R.id.nav_home:
                                Intent dash = new Intent(getApplicationContext(), homeEvent.class);
                                startActivity(dash);
                                finish();
                                drawerLayout.closeDrawers();
                                break;


                            case R.id.nav_addEvent:
                                Intent anIntent = new Intent(getApplicationContext(), add_event.class);
                                startActivity(anIntent);
                                finish();
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.nav_myEvents:
                                Intent bIntent = new Intent(getApplicationContext(), myEvents.class);
                                startActivity(bIntent);
                                finish();
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.nav_profile:
                                Intent cIntent = new Intent(getApplicationContext(), organizerProfile.class);
                                startActivity(cIntent);
                                finish();
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.nav_editProfile:
                                Intent dIntent = new Intent(getApplicationContext(), editOrganizerProfile.class);
                                startActivity(dIntent);
                                finish();
                                drawerLayout.closeDrawers();
                                break;

                            case R.id.nav_logOut:
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                Intent cI = new Intent(getApplicationContext(), RegisterTwoActivity.class);
                                startActivity(cI);
                                drawerLayout.closeDrawers();
                                break;


                        }
                        return false;
                    }
                });
    }






    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.animator.slide_from_left, R.animator.slide_to_right);
    }

}