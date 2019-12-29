package com.company_name.wasl_project4.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company_name.wasl_project4.R;

public class get_images extends HomePage {



    ImageView event_img;
    TextView for_txt;
    TextView adress_txt;
    TextView  Time_txt;
    TextView Desc_txt;
    String for_whom;
    String Adress;
    String Time;
    String Desc;

    String img_uri;
    String selectedKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); // this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_get_images, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);


        event_img = (ImageView)findViewById(R.id.event_img);
        for_txt = findViewById(R.id.for_txt);
        adress_txt = findViewById(R.id.adress_txt);
        Time_txt = findViewById(R.id.Time_txt);
        Desc_txt = findViewById(R.id.Desc_txt);
        // String uri ="https://firebasestorage.googleapis.com/v0/b/myapp-5e601.appspot.com/o/uploads%2F1556376298097.jpg?alt=media&token=7cf1ef7c-27df-49de-bd4e-86893fc42879";
        //String ur ="https://myapp-5e601.firebaseio.com/uploads";
        Bundle b1= getIntent().getExtras();
        if (!b1.isEmpty()){
            img_uri= b1.getString("img");
            for_whom = b1.getString("For");
            Adress = b1.getString("Adress");
            Time = b1.getString("Time");
            Desc = b1.getString("Desc");
            selectedKey = b1.getString("Key");
            for_txt.setText((String.valueOf(for_whom)));
            adress_txt.setText((String.valueOf(Adress)));
            Time_txt.setText((String.valueOf(Time)));
            Desc_txt.setText((String.valueOf(Desc)));

            Log.d("View", "IMAGE URL IS:"+img_uri );

            Glide.with(getApplicationContext()).load(img_uri).into(event_img);


        }


    }
}
