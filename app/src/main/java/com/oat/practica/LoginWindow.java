package com.oat.practica;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class   LoginWindow extends DialogFragment implements DialogInterface.OnClickListener {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setView(R.layout.login_window).create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
