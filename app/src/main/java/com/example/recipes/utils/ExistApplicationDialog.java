package com.example.recipes.utils;

import android.app.AlertDialog;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;


public class ExistApplicationDialog extends AlertDialog.Builder {

    public ExistApplicationDialog(Context context, FragmentActivity activity) {
        super(context);
        super.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to close the application?")
                .setPositiveButton("Yes",(dialog, which) -> activity.finish())
                .setNegativeButton("No", null);
    }
}
