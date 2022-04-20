package com.example.testfirstlibrary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {




    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder.setTitle("Сообщение")
                .setMessage("При начале записи предыдущая будет удалена, начать?")
                .setNeutralButton("Отмена",null)
                .setPositiveButton("Да", (dialog, which) ->
                        ((MainActivity)requireActivity()).recordStart())
                .create();
    }


}
