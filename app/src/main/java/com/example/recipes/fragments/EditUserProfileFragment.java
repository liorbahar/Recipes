package com.example.recipes.fragments;

import android.graphics.Bitmap;
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
import com.example.recipes.helper.UserProfileHelper;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.User;

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

        ModelClient.instance().users.getCurrentUser().observe(getViewLifecycleOwner(), (User user) -> {
            if (user != null) {
                UserProfileHelper.ShowDetails(view, user, binding.userProfileAvatarImv, true);
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

    private void onEditClick(FragmentEditUserProfileBinding binding, View view, String avatarUrl) {
        EditText name = view.findViewById(R.id.user_profile_name_et);
        EditText id = view.findViewById(R.id.user_profile_id_et);
        EditText email = view.findViewById(R.id.user_profile_email_et);
        User user = new User(id.getText().toString(), name.getText().toString(), email.getText().toString(), avatarUrl);

        if (isAvatarSelected) {
            Bitmap bitmap = ImageHelper.getImageViewBitmap(binding.userProfileAvatarImv);
            ModelClient.instance().uploadImage(user.id, bitmap, url -> {
                if (url != null) {
                    user.setAvatarUrl(url);
                }
                this.editUserAndNavigate(user, view);
            });
        } else {
            this.editUserAndNavigate(user, view);
        }
    }

    private void editUserAndNavigate(User user, View view) {
        ModelClient.instance().users.editUser(user, (unused) -> {
            ModelClient.instance().users.refreshCurrentUser();
            Navigation.findNavController(view).popBackStack();
            Navigation.findNavController(view).navigate(R.id.viewUserProfileFragment);
        });
    }
}