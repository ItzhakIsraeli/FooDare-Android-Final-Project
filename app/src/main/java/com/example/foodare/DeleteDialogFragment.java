package com.example.foodare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Post Alert");
        builder.setMessage("Are you sure you want delete this post?");
        builder.setPositiveButton("Delete", (dialogInterface, i) -> {
            Toast.makeText(getContext(), "Post Deleted", Toast.LENGTH_LONG).show();
        });
        return builder.create();
    }
}