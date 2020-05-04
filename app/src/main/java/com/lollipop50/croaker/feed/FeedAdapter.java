package com.lollipop50.croaker.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.Post;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private List<Post> allPosts;

    private final ItemEventsListener itemEventsListener;

    public FeedAdapter(List<Post> allPosts, ItemEventsListener itemEventsListener) {
        this.allPosts = allPosts;
        this.itemEventsListener = itemEventsListener;
        setHasStableIds(true);
    }

    public void updateAllPosts(List<Post> updatedPosts) {
        allPosts = updatedPosts;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return allPosts.get(position).getPostId().hashCode();
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_post, parent, false);
        return new FeedViewHolder(itemView, itemEventsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.bindTo(allPosts.get(position));
    }

    @Override
    public int getItemCount() {
        return allPosts.size();
    }

    interface ItemEventsListener {
        void onLikeClick(Post post, boolean isLiked);
        void onItemClick(Post post);
        void onLongItemClick(Post post);
    }
}
