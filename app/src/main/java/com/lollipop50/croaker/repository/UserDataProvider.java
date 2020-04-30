package com.lollipop50.croaker.repository;

import android.content.Context;

import com.lollipop50.croaker.repository.test.TestUserData;

public class UserDataProvider {

    private static UserData USER_DATA;

    public static UserData getUserData(Context context) {
        if (USER_DATA == null) {
            USER_DATA = new TestUserData();
        }

        return USER_DATA;
    }
}
