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
        return builder.setTitle("Caution")
                .setMessage("Save file?")
                .setNeutralButton("Cancel", null)
                .setPositiveButton("Yes", (dialog, which) ->
                        ((MainActivity)requireActivity()).saveToTheFiles())
                .create();
    }
}
