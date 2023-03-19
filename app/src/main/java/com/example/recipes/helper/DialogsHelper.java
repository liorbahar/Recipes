package com.example.recipes.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.fragment.app.FragmentActivity;


public class DialogsHelper {

    public static AlertDialog.Builder getDialog(Context context, FragmentActivity activity) {
        return new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to close the application?")
                .setPositiveButton("Yes",(dialog, which) -> activity.finish())
                .setNegativeButton("No", null);
    }
}
