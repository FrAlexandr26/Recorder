package com.example.testfirstlibrary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ChoseActionDialog extends DialogFragment {
    String first_string;
    public ChoseActionDialog(Context context){
        first_string = context.getResources().getString(R.string.chose_action_what_do_you_want);
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String get_object = getArguments().getString("object");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder
                .setTitle(R.string.chose_action_title)
                .setMessage(first_string + " " + get_object + "?" + " " + getString(R.string.if_you_suddenly))
                .setPositiveButton(R.string.chose_action_send, (dialog, which) -> {
                    ((MainActivity) requireActivity()).sendFileFromDir(get_object);
                })
                .setNeutralButton(R.string.chose_action_play, (dialog, which) -> ((MainActivity) requireActivity()).playFileFromDir(get_object))
                .setNegativeButton(R.string.chose_action_delete, (dialog, which) -> {
                    ((MainActivity) requireActivity()).confirmDelete(get_object);
                })
                .create();





    }


}
