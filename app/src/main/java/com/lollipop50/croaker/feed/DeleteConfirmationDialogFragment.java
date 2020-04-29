package com.lollipop50.croaker.feed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lollipop50.croaker.R;
import com.lollipop50.croaker.model.Post;
import com.lollipop50.croaker.repository.Repository;
import com.lollipop50.croaker.repository.RepositoryCreator;

import java.util.UUID;

public class DeleteConfirmationDialogFragment extends DialogFragment {

    private static final String KEY_ID = "id";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete)
                .setMessage(R.string.confirmation_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCurrentPost();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .create();
    }

    private void deleteCurrentPost() {
        Repository repository = RepositoryCreator.getInstance(getContext());
        UUID postId = (UUID) getArguments().getSerializable(KEY_ID);
        repository.delete(repository.getPostById(postId));
    }

    public static DeleteConfirmationDialogFragment makeInstance(Post post) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ID, post.getPostId());

        DeleteConfirmationDialogFragment dialogFragment = new DeleteConfirmationDialogFragment();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }
}
