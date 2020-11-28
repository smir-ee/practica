package com.morlag.wsbank.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.morlag.wsbank.LoginDialog;
import com.morlag.wsbank.R;
import com.morlag.wsbank.models.Valute;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder> {
    Context mContext; // Необходим для наполнения view по шаблону
    ArrayList<Valute> mValutes; // Собственно, данные

    public CurrencyAdapter(Context context, Valute[] valutes){
        mContext = context;
        mValutes = new ArrayList<Valute>(Arrays.asList(valutes));
    }
    public CurrencyAdapter(Context context, ArrayList<Valute> valutes){
        mContext = context;
        mValutes = valutes;
    }

    // Создает контейнеры для отображения информации
    @NonNull @Override
    public CurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrencyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_currency,parent,false));
    }

    // Обновляет информацию в контейнере по требованию
    @Override
    public void onBindViewHolder(@NonNull CurrencyHolder holder, int position) {
        holder.bind(mValutes.get(position));
    }

    // Возвращает количество элементов в адаптере
    @Override
    public int getItemCount() {
        return mValutes.size();
    }

    // Собственно, контейнер для отображения информации
    class CurrencyHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "CurrencyHolder";
        NumberFormat format = NumberFormat.getNumberInstance();

        ImageView iconVal;
        TextView valCharCode;
        TextView valName;
        TextView valBuy;
        TextView valSell;

        public CurrencyHolder(@NonNull View itemView) {
            super(itemView);
            iconVal = itemView.findViewById(R.id.iconValute);
            valCharCode = itemView.findViewById(R.id.txtValuteEng);
            valName = itemView.findViewById(R.id.txtValuteRus);
            valBuy = itemView.findViewById(R.id.txtBuy);
            valSell = itemView.findViewById(R.id.txtSell);
        }

        public void bind(Valute v){
            valCharCode.setText(v.getCharCode());
            valName.setText(v.getName());
            Double value = Double.parseDouble(v.getValue().replace(",","."));
            valBuy.setText(format.format(value - (value * (Valute.COEFFICIENT-1))));
            valSell.setText(format.format( value * Valute.COEFFICIENT ));
            try {
                iconVal.setImageDrawable(
                        Drawable.createFromStream(
                                mContext.getAssets().open("flags/" + v.getCharCode() + ".png"),
                                null));
            }
            catch (Exception ex){
                Log.d(TAG, "bind: ",ex);
                try {
                    iconVal.setImageDrawable(
                            Drawable.createFromStream(
                                    mContext.getAssets().open("flags/Unknown.png"),
                                    null));
                }
                catch (Exception ex1){
                    Log.d(TAG, "bind: ",ex1);
                }
            }
        }
    }
}
