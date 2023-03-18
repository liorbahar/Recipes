package com.example.recipes.helper;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.recipes.R;
import com.example.recipes.models.interfaces.IImageModel;
import com.squareup.picasso.Picasso;


public class ImageHelper {
    public static void insertImageByUrl(IImageModel imageModel, ImageView imageView) {
        if (!imageModel.getAvatarUrl().equals("")) {
            Picasso.get().load(imageModel.getAvatarUrl()).placeholder(R.drawable.add_image_avatar).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.add_image_avatar);
        }
    }

    public static Bitmap getImageViewBitmap(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }
}
