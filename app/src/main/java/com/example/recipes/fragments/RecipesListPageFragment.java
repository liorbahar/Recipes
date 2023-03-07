package com.example.recipes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.helper.models.ModelClient;

import java.io.Serializable;

public class RecipesListPageFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes_list_page, container, false);

        ModelClient.instance().recipes.getAllRecipes(recipes -> {
            RecipesListFragment myFrag = new RecipesListFragment();
            myFrag.setRecipes(recipes);
            Bundle bundle = new Bundle();
            bundle.putBoolean("hasAccess",true);
            myFrag.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction tran = manager.beginTransaction();
            tran.add(R.id.main_fragment_container, myFrag);
            tran.commit();

        });

        return view;
    }
}