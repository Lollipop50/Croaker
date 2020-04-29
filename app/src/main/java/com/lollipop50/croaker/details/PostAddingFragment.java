package com.lollipop50.croaker.details;

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

import static android.app.Activity.RESULT_OK;

public class PostAddingFragment extends Fragment {

    private static final int RESULT_PICK_IMG = 1;

    private Button cancelAddingButton;
    private Button croakButton;
    private EditText postTextEditor;
    private Button addPictureButton;
    private ImageView addedPictureView;

    private String postText = "";
    private String postPicturePath = "";

    private User user;

    public PostAddingFragment() {
        super(R.layout.fragment_post_adding);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelAddingButton = view.findViewById(R.id.cancel_adding_button);
        croakButton = view.findViewById(R.id.croak_button);
        croakButton.setEnabled(false);
        postTextEditor = view.findViewById(R.id.post_text_editor);
        addPictureButton = view.findViewById(R.id.add_picture_button);
        addedPictureView = view.findViewById(R.id.added_picture_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cancelAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePostEditor();
            }
        });

        croakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPost();
                closePostEditor();
            }
        });

        postTextEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    postText = s.toString();
                    croakButton.setEnabled(true);
                } else {
                    croakButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        });
    }

    private void addNewPost() {
        user = new User();  //Replace it with getting user from other repository
        Post newPost = new Post();
        newPost.setAvatar(user.getAvatar());
        newPost.setUsername(user.getUsername());
        newPost.setPostText(postText);
        newPost.setPostPicture(postPicturePath);

        Repository repository = RepositoryCreator.getInstance(getContext());
        repository.add(newPost);
    }

    private void closePostEditor() {
        getParentFragmentManager().popBackStack();
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
                postPicturePath = cursor.getString(columnIndex);
                cursor.close();

                addedPictureView.setImageBitmap(BitmapFactory.decodeFile(postPicturePath));

                croakButton.setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), R.string.fail_message, Toast.LENGTH_SHORT).show();
        }
    }
}