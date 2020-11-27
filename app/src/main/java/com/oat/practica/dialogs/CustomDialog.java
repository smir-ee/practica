package com.oat.practica.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.oat.practica.R;
import com.oat.practica.interfaces.tryUserData;

public class CustomDialog extends DialogFragment {

    private tryUserData userData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userData = (tryUserData) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View v = (View) getActivity().getLayoutInflater().inflate(R.layout.dialog_login,  null);

        return builder.setView(v)
                .setNegativeButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userData.postUserData(
                                ((EditText) v.findViewById(R.id.editText_login)).getText().toString(),
                                ((EditText) v.findViewById(R.id.editText_password)).getText().toString()
                        );
                    }
                })
                .setPositiveButton("Отмена", null)
                .create();
    }
}
