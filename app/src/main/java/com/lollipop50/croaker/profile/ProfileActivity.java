package com.lollipop50.croaker.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.details.PostDetailsFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        getSupportActionBar().setTitle(R.string.menu_profile);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.editor_fragment_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.editor_fragment_container, new ProfileFragment())
                    .commit();
        }
    }
}
