package com.morlag.wsbank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.morlag.wsbank.R;

public class LoginDialog extends DialogFragment {

    public static final String TAG = "LoginDialog";
    EditText etLogin;
    EditText etPassword;

    @NonNull @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View info = getActivity().getLayoutInflater().inflate(R.layout.login_dialog,null,false);
        etLogin = info.findViewById(R.id.etLogin);
        etPassword = info.findViewById(R.id.etPassword);

        // Стандартная реализация диалогов
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.authorization)
                .setMessage(R.string.login_title)
                .setView(info)
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String login = etLogin.getText().toString();
                        String password = etPassword.getText().toString();
                        //отправить логин и пароль
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        return builder.create();
    }
}
