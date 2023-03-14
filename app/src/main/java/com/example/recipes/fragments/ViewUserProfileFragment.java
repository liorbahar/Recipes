package com.example.recipes.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.recipes.R;
import com.example.recipes.databinding.FragmentViewUserProfileBinding;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.User;

import java.util.List;

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

        ModelClient.instance().users.getCurrentUser();
        ModelClient.instance().users.getCurrentUser().observe(getViewLifecycleOwner(), (List<User> users) -> {
            ShowDetails(view, users.get(0));
        });

        binding.viewUserProfileSignoutBtn.setOnClickListener(view1 -> {
            ModelClient.instance().users.signOutUser();
            Navigation.findNavController(view).navigate(R.id.action_viewUserProfileFragment_to_loginFragment);
        });

        binding.viewUserProfileEditBtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_viewUserProfileFragment_to_editUserProfileFragment);
        });

        return view;
    }

    public void ShowDetails(View view, User user) {
        EditText name = view.findViewById(R.id.user_profile_name_et);
        EditText id = view.findViewById(R.id.user_profile_id_et);
        EditText email = view.findViewById(R.id.user_profile_email_et);

        name.setEnabled(false);
        id.setEnabled(false);
        email.setEnabled(false);

        name.setText(user.getName());
        id.setText(user.getId());
        email.setText(user.getEmail());
    }

}