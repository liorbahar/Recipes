package com.example.recipes.helper;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.recipes.R;
import com.example.recipes.models.Recipe;
import com.example.recipes.models.interfaces.IImageModel;
import com.squareup.picasso.Picasso;


public class ImageHelper {
    public static void insertImageByUrl(IImageModel imageModel, ImageView recipeImage) {
        if (!imageModel.getAvatarUrl().equals("")) {
            Picasso.get().load(imageModel.getAvatarUrl()).placeholder(R.drawable.add_image_avatar).into(recipeImage);
        } else {
            recipeImage.setImageResource(R.drawable.add_image_avatar);
        }
    }
}
