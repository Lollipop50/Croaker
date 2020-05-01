package com.lollipop50.croaker.repository;

import android.widget.ImageView;

import com.lollipop50.croaker.model.User;

public interface UserData {

    User getCurrentUser();

    void setUserData(User user);
}
