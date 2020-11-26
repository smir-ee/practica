package com.skxrb1ud.bank.models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.skxrb1ud.bank.R;

public abstract class Login extends Dialog implements View.OnClickListener {
    Context context;
    public Login(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    public String getLogin(){
        TextView textView = findViewById(R.id.et_Login);
        return textView.getText().toString();
    }
    public String getPassword(){
        TextView textView = findViewById(R.id.et_Password);
        return textView.getText().toString();
    }
    public abstract void run(String login, String password);
    @NonNull
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_login);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            run(getLogin(),getPassword());
        }
        cancel();
    }
}