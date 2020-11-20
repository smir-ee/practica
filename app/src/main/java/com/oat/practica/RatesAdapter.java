package com.oat.practica;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class RatesAdapter extends BaseAdapter {
    private LayoutInflater LInflater;
    private ArrayList<Rate> listRates;
    Context c;

    public RatesAdapter(Context context, ArrayList<Rate> data){
        c = context;
        listRates = data;
        LInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listRates.size();
    }

    @Override
    public Rate getItem(int position) {
        return listRates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View v = convertView;
        if ( v == null){
            holder = new ViewHolder();
            v = LInflater.inflate(R.layout.rates_list_item, parent, false);
            holder.name = (TextView) v.findViewById(R.id.txtName);
            holder.code = ((TextView) v.findViewById(R.id.txtCode));
            holder.txtBuy = ((TextView) v.findViewById(R.id.txtBuy));
            holder.txtSell = ((TextView) v.findViewById(R.id.txtSell));
            holder.buy = ((ImageView) v.findViewById(R.id.imgBuy));
            holder.sell = ((ImageView) v.findViewById(R.id.imgSell));
            holder.flag = ((ImageView) v.findViewById(R.id.imgIco));
            v.setTag(holder);
        }
        holder = (ViewHolder)v.getTag();
        Rate rate = getData(position);
        holder.name.setText(rate.getNameText());
        holder.code.setText(rate.Code);
        holder.txtBuy.setText(rate.getPriceBuy());
        holder.txtSell.setText(rate.getPriceSell());
        holder.buy.setImageDrawable(ContextCompat.getDrawable(c, rate.isBuyUP ? R.drawable.ratesup : R.drawable.ratesdown));
        holder.sell.setImageDrawable(ContextCompat.getDrawable(c, rate.isSellUP ? R.drawable.ratesup : R.drawable.ratesdown));
        holder.flag.setImageDrawable(getFlag(rate.Code));
        return v;
    }

    public Drawable getFlag(String code) {
        Drawable img = ContextCompat.getDrawable(c, R.drawable.flagico);
        try {
            img = Drawable.createFromStream(c.getAssets().open("flags/" + code + ".png"), null);
        } catch (Exception e) {
            try {img = Drawable.createFromStream(c.getAssets().open("flags/Unknown.png"), null); } catch(Exception ex) {}
        }
        return img;
    }

    Rate getData(int position){
        return (getItem(position));
    }

    private static class ViewHolder {
        private TextView name;
        private TextView code;
        private TextView txtBuy;
        private TextView txtSell;
        private ImageView buy;
        private ImageView sell;
        private ImageView flag;
    }
}