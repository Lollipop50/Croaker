package com.lollipop50.croaker.feed;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.Post;
import com.lollipop50.croaker.repository.Repository;
import com.lollipop50.croaker.repository.RepositoryCreator;
import com.lollipop50.croaker.repository.test.TestRepository;

public class FeedFragment extends Fragment {

    private RecyclerView feedRecyclerView;
    private FeedAdapter feedAdapter;

    private Repository repository;

    public FeedFragment() {
        super(R.layout.fragment_feed);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = RepositoryCreator.getInstance(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        feedRecyclerView = view.findViewById(R.id.feedRecyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        feedAdapter = new FeedAdapter(
                RepositoryCreator.getInstance(getContext()).getAllPosts(),
                itemEventsListener
        );

        feedRecyclerView.setAdapter(feedAdapter);

        /*for (int i = 0; i < 9; i++) {
            repository.add(TestRepository.generateDefaultPost());
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();
        repository.addListener(repositoryListener);
    }

    @Override
    public void onStop() {
        repository.removeListener(repositoryListener);
        super.onStop();
    }

    private final Repository.Listener repositoryListener = new Repository.Listener() {
        @Override
        public void onDataChanged() {
            feedAdapter.updateAllPosts(repository.getAllPosts());
        }
    };

    private final FeedAdapter.ItemEventsListener itemEventsListener = new FeedAdapter.ItemEventsListener() {
        @Override
        public void onItemClick(Post post) {
            // Show post
        }

        @Override
        public void onLongItemClick(Post post) {
            // Show delete dialog
        }
    };
}
