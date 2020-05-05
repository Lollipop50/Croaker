package com.lollipop50.croaker.repository.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lollipop50.croaker.model.User;
import com.lollipop50.croaker.repository.UserData;

import java.lang.reflect.Type;

public class SharedPreferencesUserData implements UserData {

    private static final String KEY_USER_DATA = "user_data";

    private final SharedPreferences userDataPreferences;

    private Gson gson = new Gson();

    public SharedPreferencesUserData(Context context) {
        userDataPreferences = context
                .getSharedPreferences("userdata.prefs", Context.MODE_PRIVATE);
    }

    @Override
    public User getCurrentUser() {
        if (userDataPreferences.contains(KEY_USER_DATA)) {
            String encodedUserData = userDataPreferences.getString(KEY_USER_DATA, null);

            Type type = new TypeToken<User>() {
            }.getType();

            return gson.fromJson(encodedUserData, type);
        } else {
            return new User();
        }
    }

    @Override
    public void setUserData(User newUserData) {
        userDataPreferences.edit()
                .putString(KEY_USER_DATA, gson.toJson(newUserData))
                .apply();
    }
}
