package com.example.recipes.fragments.authentication;

import com.example.recipes.MainActivity;
import com.example.recipes.R;
import com.example.recipes.databinding.FragmentLogInBinding;
import com.example.recipes.helper.DialogsHelper;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.helper.models.UserModel;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class LogInFragment extends Fragment {
    private FragmentLogInBinding binding;
    ProgressDialog pd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pd = new ProgressDialog(getActivity());
        ((MainActivity) getActivity()).setBottomNavigationVisibility(view.GONE);
        ((MainActivity) getActivity()).hideSupportActionBar();

        binding.registerUser.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        binding.login.setOnClickListener(v -> {
            String txt_email = binding.email.getText().toString();
            String txt_password = binding.password.getText().toString();

            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(getContext(),"Empty Credentials!",Toast.LENGTH_LONG).show();
            } else {
                loginUser(txt_email, txt_password, view);
            }
        });

        this.listenToBackButtonClick();

        return view;
    }

    private void loginUser(String email, String password, View view) {
        pd.setMessage("Please wait...");
        pd.show();

        ModelClient.instance().users.loginUser(email, password, new UserModel.LoginListener() {
            @Override
            public void onComplete() {
                pd.dismiss();
                ModelClient.instance().users.refreshCurrentUser();
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_recipesListPageFragment);
            }

            @Override
            public void onFailed(String err) {
                pd.dismiss();
                Toast.makeText(getContext(),err,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listenToBackButtonClick() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DialogsHelper.getDialog(getContext(), getActivity()).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}