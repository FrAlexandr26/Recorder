package com.example.testfirstlibrary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {




    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Оповещение")
                .setMessage("При начале записи предыдущая закроеться, закрывать?")
                .setPositiveButton("Да",null)
                .setNeutralButton("Отмена", (dialog, which) -> {
                    MainActivity mn = new MainActivity();
                    mn.recordStart();
                })
                .create();
    }


}
