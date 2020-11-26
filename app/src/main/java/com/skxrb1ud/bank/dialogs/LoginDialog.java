package com.skxrb1ud.bank.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.skxrb1ud.bank.R;

public abstract class LoginDialog extends Dialog implements View.OnClickListener {
    Context context;
    public LoginDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    public String getLogin(){
        TextView textView = findViewById(R.id.loginInput);
        return textView.getText().toString();
    }
    public String getPassword(){
        TextView textView = findViewById(R.id.passwordInput);
        return textView.getText().toString();
    }
    public abstract void run(String login, String password);
    @NonNull
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.login_dialog);
        findViewById(R.id.okay).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        return builder.setView(R.layout.login_dialog)
//                .setPositiveButton("Войти", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                })
//                .create();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.okay){
            run(getLogin(),getPassword());
        }
        cancel();
    }
}
