package com.example.Recorder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ConfirmActionDialog extends DialogFragment {
    Context context;
    public ConfirmActionDialog(Context context){
        this.context = context;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Діалог підтвредження видалення  файлу
        String get_object = getArguments().getString("object");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder.setTitle(context.getString(R.string.confirm_delete_title))
                .setMessage(context.getString(R.string.confirm_delete) + " " + get_object + "?")
                .setNegativeButton(context.getString(R.string.cancel),null)
                .setPositiveButton(context.getString(R.string.confirm), (dialog, which) ->
                        ((MainActivity)requireActivity()).deleteFileFromDir(get_object))
                .create();
    }
}

