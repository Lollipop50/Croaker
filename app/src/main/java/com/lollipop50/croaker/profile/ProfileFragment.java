package com.lollipop50.croaker.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.User;
import com.lollipop50.croaker.repository.UserData;
import com.lollipop50.croaker.repository.UserDataProvider;
import com.lollipop50.croaker.repository.test.TestRepository;
import com.lollipop50.croaker.repository.test.TestUserData;

import java.io.File;

public class ProfileFragment extends Fragment {

    private ImageView profileAvatarView;
    private TextView profileUsernameView;
    private Button profileEditButton;
    private TextView profileBioView;

    private UserData userData;
    private User currentUser;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userData = UserDataProvider.getUserData(getContext());
        currentUser = userData.getCurrentUser();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileAvatarView = view.findViewById(R.id.profile_avatar_view);
        profileUsernameView = view.findViewById(R.id.profile_username_view);
        profileEditButton = view.findViewById(R.id.profile_edit_button);
        profileBioView = view.findViewById(R.id.profile_bio_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TestUserData.setAvatarFromFile(currentUser.getAvatar(), profileAvatarView);

        profileUsernameView.setText(currentUser.getUsername());

        profileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        profileBioView.setText(currentUser.getBio());
    }

    private void editProfile() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProfileEditingFragment())
                .addToBackStack(null)
                .commit();
    }
}
