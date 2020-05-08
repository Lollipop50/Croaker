package com.lollipop50.croaker.profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.Post;
import com.lollipop50.croaker.model.User;
import com.lollipop50.croaker.repository.Repository;
import com.lollipop50.croaker.repository.RepositoryCreator;
import com.lollipop50.croaker.repository.UserData;
import com.lollipop50.croaker.repository.UserDataProvider;
import com.lollipop50.croaker.repository.test.TestRepository;

import static android.app.Activity.RESULT_OK;

public class ProfileEditingFragment extends Fragment {

    private final static String KEY_PICKED_PICTURE = "picked_picture";
    private static final int RESULT_PICK_IMG = 2;

    private Button cancelEditingButton;
    private Button saveButton;
    private ImageView avatarPreview;
    private EditText usernameEditor;
    private EditText bioEditor;

    private UserData userData;
    private User currentUser;

    private String avatarPath;
    private String newUsername;
    private String bioText;

    public ProfileEditingFragment() {
        super(R.layout.fragment_profile_editing);
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

        cancelEditingButton = view.findViewById(R.id.cancel_editing_button);
        saveButton = view.findViewById(R.id.save_button);

        avatarPreview = view.findViewById(R.id.avatar_preview);
        avatarPath = currentUser.getAvatar();
        if (savedInstanceState != null) {
            avatarPath = savedInstanceState.getString(KEY_PICKED_PICTURE);
            TestRepository.setPictureFromFile(avatarPath, avatarPreview);
        }

        usernameEditor = view.findViewById(R.id.username_editor);
        newUsername = currentUser.getUsername();
        usernameEditor.setText(newUsername);

        bioEditor = view.findViewById(R.id.bio_editor);
        bioText = currentUser.getBio();
        bioEditor.setText(bioText);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cancelEditingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeProfileEditor();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
                closeProfileEditor();
            }
        });

        avatarPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });

        usernameEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    newUsername = s.toString();
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bioEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bioText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_PICKED_PICTURE, avatarPath);
    }

    private void closeProfileEditor() {
        getActivity().finish();
    }

    private void saveChanges() {
        currentUser.setAvatar(avatarPath);
        currentUser.setUsername(newUsername);
        currentUser.setBio(bioText);

        userData.setUserData(currentUser);

        updateFeed();
    }

    private void updateFeed() {
        Repository repository = RepositoryCreator.getInstance(getContext());
        for (Post post : repository.getAllPosts()) {
            post.setAvatar(currentUser.getAvatar());
            post.setUsername(currentUser.getUsername());
            repository.update(post);
        }
    }

    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_PICK_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_PICK_IMG && resultCode == RESULT_OK && data != null) {
                Uri pickedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(pickedImage,
                        filePathColumn,
                        null,
                        null,
                        null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                avatarPath = cursor.getString(columnIndex);
                cursor.close();

                TestRepository.setPictureFromFile(avatarPath, avatarPreview);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), R.string.fail_message, Toast.LENGTH_SHORT).show();
        }
    }
}
