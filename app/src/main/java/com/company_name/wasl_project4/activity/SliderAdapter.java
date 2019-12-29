package com.company_name.wasl_project4.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company_name.wasl_project4.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //arrays
    public int[] slide_images ={
            R.drawable.slide_img1,
            R.drawable.slide_img2,
            R.drawable.slide_img3
    };

    public String[] slide_desc = {
            "Are You Lost and Want to Know About Every Events happen in Taibah University",
            "Are You Tired from Exam and Want to have Fun Without any Cost and at the same have a good experience for your future ",
            "Wasl is a right place for you"

    };



    @Override
    public int getCount() {
        return slide_desc.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==(ConstraintLayout) o ;
    }

    public Object instantiateItem(ViewGroup container, int position){

        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_layout,container, false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_img);
        TextView slideDesc =(TextView) view.findViewById(R.id.slide_desc);
        slideImageView.setImageResource(slide_images[position]);
        slideDesc.setText(slide_desc[position]);

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
