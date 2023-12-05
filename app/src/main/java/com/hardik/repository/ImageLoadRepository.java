package com.hardik.repository;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoadRepository {
    private static ImageLoadRepository instance;

    private ImageLoadRepository() {
    }
    public static synchronized ImageLoadRepository getInstance() {
        if (instance == null) {
            instance = new ImageLoadRepository();
        }
        return instance;
    }
    public void setImageUsingGlide(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .placeholder(android.R.drawable.ic_lock_idle_charging)
                .error(android.R.drawable.stat_notify_error)
                .centerCrop()
//                .fitCenter()
                .into(imageView);

    }

}
