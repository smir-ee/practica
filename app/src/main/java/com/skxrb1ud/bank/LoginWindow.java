package com.skxrb1ud.bank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class LoginWindow extends DialogFragment implements DialogInterface.OnClickListener {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setView(R.layout.login_window).create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
