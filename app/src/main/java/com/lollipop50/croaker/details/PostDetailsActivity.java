package com.lollipop50.croaker.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.feed.FeedFragment;
import com.lollipop50.croaker.profile.ProfileFragment;

import java.util.UUID;

public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        getSupportActionBar().setTitle(R.string.post_adding_title);

        UUID postId = (UUID) getIntent().getSerializableExtra(FeedFragment.KEY_SELECTED_POST);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.editor_fragment_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.editor_fragment_container, PostDetailsFragment.makeInstance(postId))
                    .commit();
        }
    }
}
