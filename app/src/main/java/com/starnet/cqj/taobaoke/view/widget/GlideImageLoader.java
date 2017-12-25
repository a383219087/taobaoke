package com.starnet.cqj.taobaoke.view.widget;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by Administrator on 2017/12/25.
 */

public class GlideImageLoader implements ImageLoader{
    @Override
    public void displayImage(Activity activity, String s, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(Uri.fromFile(new File(s)))
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
