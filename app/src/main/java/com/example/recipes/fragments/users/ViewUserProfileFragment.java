package com.example.recipes.fragments.users;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.databinding.FragmentViewUserProfileBinding;
import com.example.recipes.utils.ExistApplicationDialog;
import com.example.recipes.utils.UserProfileHelper;
import com.example.recipes.model.ModelClient;
import com.example.recipes.dto.User;

public class ViewUserProfileFragment extends Fragment {
    FragmentViewUserProfileBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewUserProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ModelClient.instance().users.getCurrentUser().observe(getViewLifecycleOwner(), (User user) -> {
            if(user != null){
                UserProfileHelper.ShowDetails(view, user, binding.userProfileAvatarImg, true);
            }
        });

        binding.viewUserProfileSignoutBtn.setOnClickListener(view1 -> {
            ModelClient.instance().users.clearUser();
            ModelClient.instance().users.signOutUser();
            Navigation.findNavController(view).navigate(R.id.action_viewUserProfileFragment_to_loginFragment);
        });

        binding.viewUserProfileEditBtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_viewUserProfileFragment_to_editUserProfileFragment);
        });

        this.listenToBackButtonClick();

        return view;
    }

    private void listenToBackButtonClick(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new ExistApplicationDialog(getContext(), getActivity()).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}