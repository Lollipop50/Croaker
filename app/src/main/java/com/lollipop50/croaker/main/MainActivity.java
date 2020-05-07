package com.lollipop50.croaker.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lollipop50.croaker.R;
import com.lollipop50.croaker.bookmarks.BookmarksFragment;
import com.lollipop50.croaker.feed.FeedFragment;
import com.lollipop50.croaker.profile.ProfileFragment;
import com.lollipop50.croaker.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            makeTransaction(fragmentManager, new FeedFragment());
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case (R.id.action_feed):
                                makeTransaction(fragmentManager, new FeedFragment());
                                actionBar.setTitle(R.string.app_name);
                                break;

                            case (R.id.action_search):
                                makeTransaction(fragmentManager, new SearchFragment());
                                actionBar.setTitle(R.string.menu_search);
                                break;

                            case (R.id.action_bookmarks):
                                makeTransaction(fragmentManager, new BookmarksFragment());
                                actionBar.setTitle(R.string.menu_bookmarks);
                                break;

                            case (R.id.action_profile):
                                makeTransaction(fragmentManager, new ProfileFragment());
                                actionBar.setTitle(R.string.menu_profile);
                                break;
                        }
                        return true;
                    }
                });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        10);
            }
        }
    }

    private void makeTransaction(FragmentManager fragmentManager, Fragment fragment) {
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
