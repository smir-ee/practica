package com.morlag.wsbank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.morlag.wsbank.R;
import com.morlag.wsbank.models.Bankomat;
import com.morlag.wsbank.models.Valute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankomatsAdapter extends RecyclerView.Adapter<BankomatsAdapter.BankomatHolder> {
    Context mContext; // Необходим для наполнения view по шаблону
    ArrayList<Bankomat> mBankomats; // Собственно, данные

    public BankomatsAdapter(Context context, Bankomat[] bankomats){
        mContext = context;
        mBankomats = new ArrayList<>(Arrays.asList(bankomats));
    }
    public BankomatsAdapter(Context context, ArrayList<Bankomat> bankomats){
        mContext = context;
        mBankomats = bankomats;
    }

    @NonNull
    @Override
    public BankomatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BankomatHolder(LayoutInflater.from(mContext).inflate(R.layout.item_point,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BankomatHolder holder, int position) {
        holder.bind(mBankomats.get(position));
    }

    @Override
    public int getItemCount() {
        return mBankomats.size();
    }

    // Собственно, контейнер для отображения информации
    class BankomatHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "CurrencyHolder";

        TextView address;
        TextView type;
        TextView isWorkNow;
        TextView workingTime;

        public BankomatHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.txtAddress);
            type = itemView.findViewById(R.id.txtType);
            isWorkNow = itemView.findViewById(R.id.txtIsWork);
            workingTime = itemView.findViewById(R.id.txtWorkingTime);
        }

        public void bind(Bankomat b){ // Устанавливает актуальные значения
            String[] adr = b.getFullAddressRu().split(",");
            address.setText(adr.length >= 2 ? b.getCityRU() + ", " + adr[adr.length-2] + ", " + adr[adr.length-1] : b.getFullAddressRu());
            type.setText(b.getType().equals("ATM") ? "Банкомат" : "Отделение");
            boolean isWork = b.isWorkNow();
            isWorkNow.setText(isWork ? "Работает" : "Закрыто");
            isWorkNow.setTextColor(mContext.getResources().getColor(isWork ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
            workingTime.setText(b.getWorkTimeToday());
        }
    }
}
