package com.lollipop50.croaker.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.Post;
import com.lollipop50.croaker.profile.ProfileActivity;
import com.lollipop50.croaker.profile.ProfileFragment;
import com.lollipop50.croaker.repository.Repository;
import com.lollipop50.croaker.repository.RepositoryCreator;
import com.lollipop50.croaker.repository.test.TestUserData;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class PostDetailsFragment extends Fragment {

    private static final String KEY_ID = "id";

    private ImageView avatarView;
    private TextView usernameView;
    private TextView dateView;
    private TextView postTextView;
    private ImageView postPictureView;
    private CheckBox isLikedView;
    private CheckBox isBookmarkedView;

    private Repository repository;
    private Post post;
    private UUID postId;

    public PostDetailsFragment() {
        super(R.layout.fragment_post_details);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        postId = (UUID) arguments.getSerializable(KEY_ID);

        repository = RepositoryCreator.getInstance(getContext());
        post = repository.getPostById(postId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatarView = view.findViewById(R.id.details_avatar_view);
        usernameView = view.findViewById(R.id.details_username_view);
        dateView = view.findViewById(R.id.details_date_view);
        postTextView = view.findViewById(R.id.details_post_text_view);
        postPictureView = view.findViewById(R.id.details_post_picture_view);
        isLikedView = view.findViewById(R.id.details_is_liked_view);
        isBookmarkedView = view.findViewById(R.id.details_is_bookmarked_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });
        TestUserData.setAvatarFromFile(post.getAvatar(), avatarView);

        usernameView.setText(post.getUsername());

        dateView.setText(formatPostDate());

        postTextView.setText(post.getPostText());

        File postPictureFile = new File(post.getPostPicture());
        if (postPictureFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(postPictureFile.getAbsolutePath());
            postPictureView.setImageBitmap(bitmap);
        }

        isLikedView.setChecked(post.isLiked());
        isLikedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                post.setLiked(isChecked);
                updateCurrentPost();
            }
        });

        isBookmarkedView.setChecked(post.isBookmarked());
        isBookmarkedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                post.setBookmarked(isChecked);
                updateCurrentPost();
            }
        });
    }

    private String formatPostDate() {
        Date postDate = post.getPostDate();
        java.text.DateFormat timeFormat = DateFormat.getTimeFormat(getContext());
        java.text.DateFormat dateFormat = DateFormat.getMediumDateFormat(getContext());

        String result = timeFormat.format(postDate) + " • " + dateFormat.format(postDate);

        return result;
    }

    private void updateCurrentPost() {
        repository.update(post);
    }

    public static PostDetailsFragment makeInstance(UUID postId) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ID, postId);

        PostDetailsFragment fragment = new PostDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
