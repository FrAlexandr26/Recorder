package com.example.Recorder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ChoseActionDialog extends DialogFragment {
    Context context;
    public ChoseActionDialog(Context context){this.context = context;}
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Діалог вибору дій із обранним файлом
        String get_object = getArguments().getString("object");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder
                .setTitle(R.string.chose_action_title)
                .setMessage(context.getResources().getString(R.string.chose_action_what_do_you_want) + " " + get_object + "?" + " " + context.getResources().getString(R.string.if_you_suddenly))
                .setPositiveButton(R.string.chose_action_send, (dialog, which) -> {
                    ((MainActivity)requireActivity()).sendFileFromDir(get_object);
                })
                .setNeutralButton(R.string.chose_action_play, (dialog, which) -> ((MainActivity) requireActivity()).playFileFromDir(get_object))
                .setNegativeButton(R.string.chose_action_delete, (dialog, which) -> {
                    ((MainActivity)requireActivity()).confirmDelete(get_object);
                })
                .create();





    }


}
