package com.example.recipes.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.recipes.R;
import com.example.recipes.dto.interfaces.IImageModel;
import com.squareup.picasso.Picasso;


public class ImageHelper {
    public static void insertImageByUrl(IImageModel imageModel, ImageView imageView) {
        if (!imageModel.getAvatarUrl().equals("")) {
            try {
                Picasso.get().load(imageModel.getAvatarUrl()).placeholder(R.drawable.add_image_avatar).into(imageView);
            }
            catch (Exception e){
                imageView.setImageResource(R.drawable.add_image_avatar);
            }
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
