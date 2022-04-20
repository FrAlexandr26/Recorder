package com.example.testfirstlibrary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ChoseActionDialog extends DialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String get_object = getArguments().getString("object");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder
                .setTitle("Выберите действие")
                .setMessage("Что вы хотите сделать с файлом" + " " + get_object + "?" + " " + "Если ничего - нажмите на область вокруг диаолгового окна")
                .setPositiveButton("Отправить", (dialog, which) -> {
                    ((MainActivity)requireActivity()).sendFileFromDir(get_object);
                })
                .setNeutralButton("Воспроизвести", null)
                .setNegativeButton("Удалить", (dialog, which) -> {
                    ((MainActivity)requireActivity()).deleteFileFromDir(get_object);
                })
                .create();




    }


}
