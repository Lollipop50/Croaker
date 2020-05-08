package com.lollipop50.croaker.bookmarks;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.details.PostDetailsFragment;
import com.lollipop50.croaker.feed.DeleteConfirmationDialogFragment;
import com.lollipop50.croaker.feed.FeedAdapter;
import com.lollipop50.croaker.model.Post;
import com.lollipop50.croaker.repository.Repository;
import com.lollipop50.croaker.repository.RepositoryCreator;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends Fragment {

    private LinearLayout noBookmarksView;
    private RecyclerView bookmarksRecyclerView;

    private FeedAdapter bookmarksAdapter;
    private Repository repository;

    private List<Post> bookmarkedPosts;

    public BookmarksFragment() {
        super(R.layout.fragment_bookmarks);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = RepositoryCreator.getInstance(getContext());
        bookmarkedPosts = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noBookmarksView = view.findViewById(R.id.no_bookmarks_view);
        bookmarksRecyclerView = view.findViewById(R.id.bookmarks_RecyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        bookmarksRecyclerView.setLayoutManager(linearLayoutManager);
        bookmarksRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)
        );

        bookmarksAdapter = new FeedAdapter(bookmarkedPosts, itemEventsListener);
        bookmarksRecyclerView.setAdapter(bookmarksAdapter);
        buildBookmarkedPostsList();
    }

    @Override
    public void onResume() {
        super.onResume();
        repository.addListener(repositoryListener);

        for (Post post : repository.getAllPosts()) {
            repository.update(post);
        }
    }

    @Override
    public void onPause() {
        repository.removeListener(repositoryListener);
        super.onPause();
    }

    private void buildBookmarkedPostsList() {
        boolean bookmarkedFound = false;
        bookmarkedPosts.clear();

        for (Post post : repository.getAllPosts()) {
            if (post.isBookmarked()) {
                bookmarkedPosts.add(post);
                bookmarkedFound = true;
            }
        }

        noBookmarksView.setVisibility(bookmarkedFound ? View.INVISIBLE : View.VISIBLE);

        bookmarksAdapter.updateAllPosts(bookmarkedPosts);
    }

    private void showPost(Post post) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, PostDetailsFragment.makeInstance(post.getPostId()))
                .addToBackStack(null)
                .commit();
    }

    private void showDeleteDialog(Post post) {
        DeleteConfirmationDialogFragment.makeInstance(post)
                .show(getParentFragmentManager(), null);
    }

    private final Repository.Listener repositoryListener = new Repository.Listener() {
        @Override
        public void onDataChanged() {
            buildBookmarkedPostsList();
        }
    };

    private final FeedAdapter.ItemEventsListener itemEventsListener = new FeedAdapter.ItemEventsListener() {
        @Override
        public void onLikeClick(Post post, boolean isLiked) {
            post.setLiked(isLiked);
            repository.update(post);
        }

        @Override
        public void onItemClick(Post post) {
            showPost(post);
        }

        @Override
        public void onLongItemClick(Post post) {
            showDeleteDialog(post);
        }
    };
}
