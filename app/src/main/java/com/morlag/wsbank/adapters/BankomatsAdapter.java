package com.morlag.wsbank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.morlag.wsbank.R;
import com.morlag.wsbank.models.Bankomat;

import java.util.ArrayList;
import java.util.Arrays;

public class BankomatsAdapter extends RecyclerView.Adapter<BankomatsAdapter.BankomatHolder> {
    Context mContext; // Необходим для наполнения view по шаблону
    ArrayList<Bankomat> mBankomats; // Собственно, данные
    OnLatLngSender mSender;
    public interface OnLatLngSender {
        public void sendLatLng(LatLng latLng);
    }

    public BankomatsAdapter(Context context, Bankomat[] bankomats, OnLatLngSender onLatLngSender){
        mContext = context;
        mBankomats = new ArrayList<>(Arrays.asList(bankomats));
        mSender = onLatLngSender;
    }
    public BankomatsAdapter(Context context, ArrayList<Bankomat> bankomats, OnLatLngSender onLatLngSender){
        mContext = context;
        mBankomats = bankomats;
        mSender = onLatLngSender;
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
        LatLng latLng;

        public BankomatHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.txtAddress);
            type = itemView.findViewById(R.id.txtType);
            isWorkNow = itemView.findViewById(R.id.txtIsWork);
            workingTime = itemView.findViewById(R.id.txtWorkingTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSender.sendLatLng(latLng);
                }
            });
        }

        public void bind(Bankomat b){ // Устанавливает актуальные значения
            address.setText(b.getNotFullAddressRu());
            type.setText(b.getType().equals("ATM") ? "Банкомат" : "Отделение");
            boolean isWork = b.isWorkNow();
            isWorkNow.setText(isWork ? "Работает" : "Закрыто");
            isWorkNow.setTextColor(mContext.getResources().getColor(isWork ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
            String time = b.getWorkTimeToday();
            workingTime.setText(time.equals("00:00 - 00:00") ? "Сегодня не работает" : String.format("Время работы: %s",time));
            latLng = b.getLatLng();
        }
    }
}
