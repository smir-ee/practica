package com.example.bank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



class CustomDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = (View) getActivity().getLayoutInflater().inflate(R.layout.login,  null);

        return builder.setView(v)
                .setNegativeButton("Добавить", null)
                .setPositiveButton("Отмена", null)
                .create();
    }
}