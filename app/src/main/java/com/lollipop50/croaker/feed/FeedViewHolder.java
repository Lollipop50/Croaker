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

import java.io.File;

public class FeedViewHolder extends RecyclerView.ViewHolder {

    private ImageView avatarView;
    private TextView usernameView;
    private TextView postTextView;
    private ImageView postPictureView;
    private CheckBox isLikedView;

    private Post currentPost;

    public FeedViewHolder(@NonNull View itemView, final FeedAdapter.ItemEventsListener itemEventsListener) {
        super(itemView);

        avatarView = itemView.findViewById(R.id.avatar_view);
        usernameView = itemView.findViewById(R.id.username_view);
        postTextView = itemView.findViewById(R.id.post_text_view);
        postPictureView = itemView.findViewById(R.id.post_picture_view);
        isLikedView = itemView.findViewById(R.id.is_liked_view);

        isLikedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentPost.setLiked(isChecked);
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEventsListener.onItemClick(currentPost);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemEventsListener.onLongItemClick(currentPost);
                return true;
            }
        });
    }

    public void bidnTo(Post post) {
        currentPost = post;

        File avatarFile = new File(currentPost.getAvatar());
        if (avatarFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(avatarFile.getAbsolutePath());
            avatarView.setImageBitmap(bitmap);
        }

        usernameView.setText(currentPost.getUsername());

        postTextView.setText(currentPost.getPostText());

        File postPictureFile = new File(currentPost.getPostPicture());
        if (postPictureFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(postPictureFile.getAbsolutePath());
            postPictureView.setImageBitmap(bitmap);
        }

        isLikedView.setChecked(currentPost.isLiked());
    }
}
