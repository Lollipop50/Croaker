package com.lollipop50.croaker.repository.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.User;
import com.lollipop50.croaker.repository.UserData;

import java.io.File;

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

    public static void setAvatarFromFile(String filePath, ImageView avatarView) {
        /*File avatarFile = new File(filePath);
        if (avatarFile.exists()) {
            return BitmapFactory.decodeFile(avatarFile.getAbsolutePath());
        } else {
            profileAvatarView.setImageResource(R.drawable.no_avatar);
        }*/

        File avatarFile = new File(filePath);
        if (avatarFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(avatarFile.getAbsolutePath());
            avatarView.setImageBitmap(bitmap);
        } else {
            avatarView.setImageResource(R.drawable.no_avatar);
        }
    }
}
