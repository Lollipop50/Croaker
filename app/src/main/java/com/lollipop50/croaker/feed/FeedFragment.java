package com.lollipop50.croaker.feed;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lollipop50.croaker.R;

public class FeedFragment extends Fragment {

    private RecyclerView feedRecyclerView;
    private FeedAdapter feedAdapter;

    // Create and add repository

    public FeedFragment() {
        super(R.layout.fragment_feed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        feedRecyclerView = view.findViewById(R.id.feedRecyclerView);
    }
}
