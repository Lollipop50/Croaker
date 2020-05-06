package com.lollipop50.croaker.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.details.PostAddingFragment;

public class ProfileEditingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        getSupportActionBar().setTitle(R.string.profile_editing_title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.editor_fragment_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.editor_fragment_container, new ProfileEditingFragment())
                    .commit();
        }
    }
}
