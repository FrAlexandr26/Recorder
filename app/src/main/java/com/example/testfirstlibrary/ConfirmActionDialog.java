package com.example.testfirstlibrary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ConfirmActionDialog extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String get_object = getArguments().getString("object");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder.setTitle(R.string.confirm_delete_title)
                .setMessage(R.string.confirm_delete + " " + get_object + "?")
                .setNegativeButton(R.string.cancel,null)
                .setPositiveButton(R.string.confirm, (dialog, which) ->
                        ((MainActivity)requireActivity()).deleteFileFromDir(get_object))
                .create();
    }
}

