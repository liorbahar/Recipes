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
import com.example.recipes.databinding.FragmentEditUserProfileBinding;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.User;

import java.util.List;

public class EditUserProfileFragment extends Fragment {
    FragmentEditUserProfileBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditUserProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ModelClient.instance().users.getCurrentUser().observe(getViewLifecycleOwner(), (List<User> users) -> {
            ShowDetails(view, users.get(0));
        });


        binding.editUserProfileSaveBtn.setOnClickListener(view1 -> {
            EditText name = view.findViewById(R.id.user_profile_name_et);
            EditText id = view.findViewById(R.id.user_profile_id_et);
            EditText email = view.findViewById(R.id.user_profile_email_et);
            User user = new User(id.getText().toString(), name.getText().toString(), email.getText().toString());

            ModelClient.instance().users.editUser(user, (unused) -> {
                ModelClient.instance().users.refreshCurrentUser();
                Navigation.findNavController(view).popBackStack();
            });
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
    }
}