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
        return builder.setTitle("Подтвердите удаление")
                .setMessage("Вы точно хотите удалить файл?" + " " + get_object)
                .setNegativeButton("Отмена",null)
                .setPositiveButton("Да", (dialog, which) ->
                        ((MainActivity)requireActivity()).deleteFileFromDir(get_object))
                .create();
    }
}

