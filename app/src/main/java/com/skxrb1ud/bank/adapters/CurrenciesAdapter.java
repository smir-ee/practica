package com.skxrb1ud.bank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skxrb1ud.bank.R;
import com.skxrb1ud.bank.models.Currency;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder> {
    private Context mContext;
    private Currency[] currencies;
    public CurrenciesAdapter(Context context, Currency[] currencies){
        mContext = context;
        this.currencies = currencies;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.currency_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Currency currency = currencies[position];
        holder.charCode.setText(currency.getCharCode());
        holder.name.setText(currency.getName());
        holder.sellPrice.setText(String.valueOf(currency.getValue()));
        holder.buyPrice.setText(String.valueOf(currency.getValue()));
    }

    @Override
    public int getItemCount() {
        return currencies.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView charCode;
        TextView name;
        TextView sellPrice;
        TextView buyPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            charCode = itemView.findViewById(R.id.charCode);
            name = itemView.findViewById(R.id.name);
            sellPrice = itemView.findViewById(R.id.sellPrice);
            buyPrice = itemView.findViewById(R.id.buyPrice);
        }
    }
}
