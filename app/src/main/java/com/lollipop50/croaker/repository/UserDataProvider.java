package com.lollipop50.croaker.repository;

import android.content.Context;

import com.lollipop50.croaker.repository.preferences.SharedPreferencesUserData;
import com.lollipop50.croaker.repository.test.TestUserData;

public class UserDataProvider {

    private static UserData USER_DATA;

    public static UserData getUserData(Context context) {
        if (USER_DATA == null) {
//            USER_DATA = new TestUserData();
            USER_DATA = new SharedPreferencesUserData(context);
        }

        return USER_DATA;
    }
}
