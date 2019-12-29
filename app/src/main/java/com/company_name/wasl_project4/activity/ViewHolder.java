package com.company_name.wasl_project4.activity;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company_name.wasl_project4.R;
import com.squareup.picasso.Picasso;


public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;
    public TextView textViewName;
    public ImageView imageView;
    public ViewHolder(View itemView) {
        super(itemView);
        mview = itemView;
    }
    public void setDetails(Context ctx , String title , String img){


        textViewName = itemView.findViewById(R.id.text_view_name);
        imageView = itemView.findViewById(R.id.image_view_upload);
        textViewName.setText(title);
        Picasso.with(ctx).load(img).into(imageView);

    }
}
