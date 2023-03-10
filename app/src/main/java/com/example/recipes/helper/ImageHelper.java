package com.example.recipes.helper;

import android.widget.ImageView;

import com.example.recipes.R;
import com.example.recipes.models.Recipe;
import com.squareup.picasso.Picasso;

public class ImageHelper {
    public static void insertImageByUrl(Recipe recipe, ImageView recipeImage) {
        if (!recipe.getAvatarUrl().equals("")) {
            Picasso.get().load(recipe.getAvatarUrl()).placeholder(R.drawable.add_image_avatar).into(recipeImage);
        } else {
            recipeImage.setImageResource(R.drawable.add_image_avatar);
        }
    }
}
