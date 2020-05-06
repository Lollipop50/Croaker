package com.lollipop50.croaker.search;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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

public class SearchFragment extends Fragment {

    private static final String KEY_SEARCHED_TEXT = "searched_text";

    private SearchView postsSearchView;
    private TextView resultsNumberView;
    private ImageView backgroundImage;
    private RecyclerView searchRecyclerView;

    private FeedAdapter searchAdapter;
    private Repository repository;

    private List<Post> searchedPosts;

    private String searchedText = "";

    public SearchFragment() {
        super(R.layout.fragment_search);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        repository = RepositoryCreator.getInstance(getContext());
        searchedPosts = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resultsNumberView = view.findViewById(R.id.results_number_view);
        backgroundImage = view.findViewById(R.id.search_background_image);
        searchRecyclerView = view.findViewById(R.id.search_RecyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL)
        );

        searchAdapter = new FeedAdapter(searchedPosts, itemEventsListener);
        searchRecyclerView.setAdapter(searchAdapter);

        if (savedInstanceState != null) {
            searchedText = savedInstanceState.getString(KEY_SEARCHED_TEXT);
            searchPostsByQuery(searchedText);
        }
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SEARCHED_TEXT, searchedText);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search_menu_item);
        postsSearchView = (SearchView) searchItem.getActionView();
        postsSearchView.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        postsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchedText = query;
                searchPostsByQuery(searchedText);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchPostsByQuery(String query) {
        if (searchedText.length() > 0) {
            boolean resultsFound = false;
            searchedPosts.clear();

            for (Post post : repository.getAllPosts()) {
                if (post.getPostText().toLowerCase().contains(query.toLowerCase())) {
                    searchedPosts.add(post);
                    resultsFound = true;
                }
            }

            String results = getString(R.string.results_text) + " " + searchedPosts.size();
            resultsNumberView.setText(results);
            backgroundImage.setVisibility(resultsFound ? View.INVISIBLE : View.VISIBLE);

            searchAdapter.updateAllPosts(searchedPosts);
        }
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
            searchPostsByQuery(searchedText);
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
