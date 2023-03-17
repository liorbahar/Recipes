package com.example.recipes.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.recipes.R;
import com.example.recipes.databinding.FragmentEditUserProfileBinding;
import com.example.recipes.helper.ImageHelper;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.User;

import java.util.List;

public class EditUserProfileFragment extends Fragment {
    FragmentEditUserProfileBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;
    String avatarUrl = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.userProfileAvatarImv.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.userProfileAvatarImv.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditUserProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ModelClient.instance().users.getCurrentUser().observe(getViewLifecycleOwner(), (List<User> users) -> {
            if (!users.isEmpty()) {
                User user = users.get(0);
                ShowDetails(view, user);
                avatarUrl = user.getAvatarUrl();
            }
        });


        binding.editUserProfileSaveBtn.setOnClickListener(view1 -> {
            this.onEditClick(binding, view, avatarUrl);
        });

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });

        return view;
    }

    public void ShowDetails(View view, User user) {
        EditText name = view.findViewById(R.id.user_profile_name_et);
        EditText id = view.findViewById(R.id.user_profile_id_et);
        EditText email = view.findViewById(R.id.user_profile_email_et);
        id.setEnabled(false);
        email.setEnabled(false);

        name.setText(user.getName());
        id.setText(user.getId());
        email.setText(user.getEmail());
        ImageHelper.insertImageByUrl(user, binding.userProfileAvatarImv);
    }

    private void onEditClick(FragmentEditUserProfileBinding binding, View view, String avatarUrl) {
        EditText name = view.findViewById(R.id.user_profile_name_et);
        EditText id = view.findViewById(R.id.user_profile_id_et);
        EditText email = view.findViewById(R.id.user_profile_email_et);
        User user = new User(id.getText().toString(), name.getText().toString(), email.getText().toString(), avatarUrl);


        if (isAvatarSelected) {
            binding.userProfileAvatarImv.setDrawingCacheEnabled(true);
            binding.userProfileAvatarImv.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) binding.userProfileAvatarImv.getDrawable()).getBitmap();
            ModelClient.instance().uploadImage(user.id, bitmap, url -> {
                if (url != null) {
                    user.setAvatarUrl(url);
                }
                ModelClient.instance().users.editUser(user, (unused) -> {
                    ModelClient.instance().users.refreshCurrentUser();
                    Navigation.findNavController(view).popBackStack();
                });
            });
        } else {
            ModelClient.instance().users.editUser(user, (unused) -> {
                ModelClient.instance().users.refreshCurrentUser();
                Navigation.findNavController(view).popBackStack();
            });
        }
    }
}