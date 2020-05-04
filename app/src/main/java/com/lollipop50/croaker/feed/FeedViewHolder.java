package com.lollipop50.croaker.feed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.Post;
import com.lollipop50.croaker.repository.test.TestUserData;

import java.io.File;

public class FeedViewHolder extends RecyclerView.ViewHolder {

    private ImageView avatarView;
    private TextView usernameView;
    private TextView postTextView;
    private ImageView postPictureView;
    private CheckBox isLikedView;

    private Post currentPost;

    private final FeedAdapter.ItemEventsListener itemEventsListener;

    public FeedViewHolder(@NonNull View itemView, FeedAdapter.ItemEventsListener itemEventsListener) {
        super(itemView);

        this.itemEventsListener = itemEventsListener;

        avatarView = itemView.findViewById(R.id.avatar_view);
        usernameView = itemView.findViewById(R.id.username_view);
        postTextView = itemView.findViewById(R.id.post_text_view);
        postPictureView = itemView.findViewById(R.id.post_picture_view);
        isLikedView = itemView.findViewById(R.id.is_liked_view);

        itemView.setOnClickListener(itemClickListener);
        itemView.setOnLongClickListener(itemLongLickListener);
    }

    public void bindTo(Post post) {
        currentPost = post;

        TestUserData.setAvatarFromFile(currentPost.getAvatar(), avatarView);

        usernameView.setText(currentPost.getUsername());

        postTextView.setText(currentPost.getPostText());

        File postPictureFile = new File(currentPost.getPostPicture());
        if (postPictureFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(postPictureFile.getAbsolutePath());
            postPictureView.setImageBitmap(bitmap);
        } else {
            postPictureView.setImageDrawable(null);
        }

        isLikedView.setOnCheckedChangeListener(null);
        isLikedView.setChecked(currentPost.isLiked());
        isLikedView.setOnCheckedChangeListener(onLikeClickListener);
    }

    private final CompoundButton.OnCheckedChangeListener onLikeClickListener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    itemEventsListener.onLikeClick(currentPost, isChecked);
                }
            };

    private final View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            itemEventsListener.onItemClick(currentPost);
        }
    };

    private final View.OnLongClickListener itemLongLickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            itemEventsListener.onLongItemClick(currentPost);
            return true;
        }
    };
}
