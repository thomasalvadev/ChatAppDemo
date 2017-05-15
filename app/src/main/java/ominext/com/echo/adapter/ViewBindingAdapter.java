package ominext.com.echo.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by LuongHH on 12/13/2016.
 */

public class ViewBindingAdapter {

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable error) {
        Glide.with(imageView.getContext()).load(url).error(error).into(imageView);
    }
}
