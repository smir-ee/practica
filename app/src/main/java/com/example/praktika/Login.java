package com.example.praktika;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class Login extends DialogFragment {
   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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