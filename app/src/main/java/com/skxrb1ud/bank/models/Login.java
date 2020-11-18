package com.skxrb1ud.bank.models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.skxrb1ud.bank.R;

public class Login extends DialogFragment {
    @RequiresApi (api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(R.layout.dialog_login)
                .setPositiveButton("Войти", null)
                .setNegativeButton("Отмена", null)
                .create();
    }
}
