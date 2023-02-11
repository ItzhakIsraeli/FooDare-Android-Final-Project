package com.example.foodare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.example.foodare.model.Model;

public class DeleteDialogFragment extends DialogFragment {

    View dialogView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        String postId = getArguments().getString("postId");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Post Alert");
        builder.setMessage("Are you sure you want delete this post?");
        builder.setPositiveButton("Delete", (dialogInterface, i) -> {
            Model.instance().deletePost(postId);
            Model.instance().refreshAllPosts();
            Toast.makeText(getContext(), "Post Deleted", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Navigation.findNavController(dialogView).popBackStack();
        });
        return builder.create();
    }

    public void addView(View view) {
        dialogView = view;
    }
}