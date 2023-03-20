package com.example.recipes.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.recipes.R;
import com.example.recipes.dto.User;

public class UserProfileHelper {
    public static void ShowDetails(View view, User user, ImageView imageView, Boolean isOnlyView) {
        EditText name = view.findViewById(R.id.user_profile_name_et);
        EditText id = view.findViewById(R.id.user_profile_id_et);
        EditText email = view.findViewById(R.id.user_profile_email_et);

        if (isOnlyView) {
            name.setEnabled(false);
        }
        id.setEnabled(false);
        email.setEnabled(false);

        name.setText(user.getName());
        id.setText(user.getId());
        email.setText(user.getEmail());
        ImageHelper.insertImageByUrl(user, imageView);
    }
}
