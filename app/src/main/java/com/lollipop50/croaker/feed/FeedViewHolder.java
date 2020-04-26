package com.lollipop50.croaker.feed;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.Post;

public class FeedViewHolder extends RecyclerView.ViewHolder {

    private ImageView avatarView;
    private TextView usernameView;
    private TextView postTextView;
    private ImageView postPictureView;
    private CheckBox isLikedView;

    private final FeedAdapter.ItemEventsListener itemEventsListener;

    private Post currentPost;

    public FeedViewHolder(@NonNull View itemView, final FeedAdapter.ItemEventsListener itemEventsListener) {
        super(itemView);

        this.itemEventsListener = itemEventsListener;

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

        // Add avatar
        usernameView.setText(currentPost.getUsername());
        postTextView.setText(currentPost.getPostText());
        // Add postPicture
        isLikedView.setChecked(currentPost.isLiked());
    }
}
