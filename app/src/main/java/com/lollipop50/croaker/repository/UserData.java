package com.lollipop50.croaker.repository;

import com.lollipop50.croaker.model.User;

public interface UserData {

    User getCurrentUser();

    void setUserData(User newUserData);
}
