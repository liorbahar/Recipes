package com.example.recipes.model.interfaces;

public interface AuthenticationListener {
    void onComplete();

    void onFailed(String err);
}
