package com.example.testfirstlibrary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DialogSaveFile extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder.setTitle(R.string.save_file_title)
                .setMessage(R.string.save_file)
                .setNeutralButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm, (dialog, which) ->
                        ((MainActivity)requireActivity()).saveToTheFiles())
                .create();
    }
}
