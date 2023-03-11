package com.example.recipes.fragments.authentication;

import com.example.recipes.MainActivity;
import com.example.recipes.R;
import com.example.recipes.databinding.FragmentLogInBinding;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.helper.models.UserModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LogInFragment extends Fragment {
    private FragmentLogInBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) getActivity()).setBottomNavigationVisibility(view.GONE);

        binding.registerUser.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        binding.login.setOnClickListener(v -> {
            String txt_email = binding.email.getText().toString();
            String txt_password = binding.password.getText().toString();

            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                //add toast
            } else {
                loginUser(txt_email, txt_password, view);
            }
        });

        return view;
    }

    private void loginUser(String email, String password, View view) {

        ModelClient.instance().users.loginUser(email, password, new UserModel.LoginListener() {
            @Override
            public void onComplete() {
                //add toast
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_recipesListPageFragment);
            }

            @Override
            public void onFailed(String err) {
                //add toast
            }
        });
    }


}