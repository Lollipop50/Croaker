package com.lollipop50.croaker.repository.test;

import com.lollipop50.croaker.model.User;
import com.lollipop50.croaker.repository.UserData;

public class TestUserData implements UserData {

    private User currentUser = new User();

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setUserData(String avatar, String username, String bio) {
        currentUser.setAvatar(avatar);
        currentUser.setUsername(username);
        currentUser.setBio(bio);
    }

    @Override
    public void generateDefaultUser() {
        currentUser.setAvatar("");
        currentUser.setUsername("User");
    }
}
