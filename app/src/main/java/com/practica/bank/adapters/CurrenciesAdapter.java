package com.practica.bank.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.practica.bank.R;
import com.practica.bank.models.Currency;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder> {
    private Context mContext;
    private Currency[] currencies;
    private Currency[] lastDay;
    public CurrenciesAdapter(Context context, Currency[] currencies, Currency[] lastDay){
        mContext = context;
        this.currencies = currencies;
        this.lastDay = lastDay;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.currency_item,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Currency currency = currencies[position];
        holder.charCode.setText(currency.getCharCode());
        holder.name.setText(currency.getName());
        holder.sellPrice.setText(String.format("%.2f",currency.getValue()* 1.4));
        holder.buyPrice.setText(String.format("%.2f",currency.getValue()* 1.25));
        Drawable drawable;
        try {
            drawable = Drawable.createFromStream(mContext.getAssets().open("flags/" + currency.getCharCode() + ".png"),null);
            holder.picture.setImageDrawable(drawable);
        }catch (Exception e){
            try {
                drawable = Drawable.createFromStream(mContext.getAssets().open("flags/Unknown.png"),null);
                holder.picture.setImageDrawable(drawable);
            }
            catch (Exception e1){

            }
        }
        Currency lastDayCurrency = lastDay[position];
        int i = 0;
        while(!lastDayCurrency.getCharCode().equals(currency.getCharCode()) && i < currencies.length){
            lastDayCurrency = lastDay[i];
            i++;
        }
        if(currency.getValue() > lastDayCurrency.getValue()){
            holder.sellArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_arrow_up));
            holder.buyArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_arrow_up));
            holder.sellArrow.setColorFilter(mContext.getColor(R.color.bankomat_on));
            holder.buyArrow.setColorFilter(mContext.getColor(R.color.bankomat_on));
        }else{
            holder.sellArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_arrow_down));
            holder.buyArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_arrow_down));
            holder.sellArrow.setColorFilter(mContext.getColor(R.color.bankomat_off));
            holder.buyArrow.setColorFilter(mContext.getColor(R.color.bankomat_off));
        }

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
        ImageView picture;
        ImageView sellArrow;
        ImageView buyArrow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            charCode = itemView.findViewById(R.id.charCode);
            name = itemView.findViewById(R.id.name);
            sellPrice = itemView.findViewById(R.id.sellPrice);
            buyPrice = itemView.findViewById(R.id.buyPrice);
            picture = itemView.findViewById(R.id.currency_picture);
            sellArrow = itemView.findViewById(R.id.sellArrow);
            buyArrow = itemView.findViewById(R.id.buyArrow);
        }
    }
}
