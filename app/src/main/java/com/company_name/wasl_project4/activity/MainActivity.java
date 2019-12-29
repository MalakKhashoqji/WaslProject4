package com.company_name.wasl_project4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company_name.wasl_project4.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager mySlider ;
    private LinearLayout mDotLayout ;
    private LinearLayout buttonsContainer ;

    private SliderAdapter slider;

    private TextView[] mDots;

    private Button signupbtn;
    private Button signinbtn;

    private int mCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySlider = (ViewPager) findViewById(R.id.viewPager);
        mDotLayout=(LinearLayout) findViewById(R.id.linearLayout);
        slider =new SliderAdapter(this);
        mySlider.setAdapter(slider);
        addDots(0);
        mySlider.addOnPageChangeListener(viewListener);


        Button goBtn =(Button)findViewById(R.id.goBtn);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), RegisterTwoActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    public void addDots (int position){

        mDots= new TextView[3];

        mDotLayout.removeAllViews();
        for(int i =0; i<mDots.length;i++){
            mDots[i] =new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(40);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }




    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}

