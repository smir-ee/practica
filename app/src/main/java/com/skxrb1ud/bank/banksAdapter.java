package com.skxrb1ud.bank;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class banksAdapter extends BaseAdapter {
    private LayoutInflater LInflater;
    private ArrayList<Bank> listBanks;
    Context c;

    public banksAdapter(Context context, ArrayList<Bank> data){
        c = context;
        listBanks = data;
        LInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listBanks.size();
    }

    @Override
    public Bank getItem(int position) {
        return listBanks.get(position);
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
            v = LInflater.inflate(R.layout.bank_list_item, parent, false);
            holder.address = (TextView) v.findViewById(R.id.address);
            holder.time = ((TextView) v.findViewById(R.id.time));
            holder.type = ((TextView) v.findViewById(R.id.type));
            holder.status = ((TextView) v.findViewById(R.id.status));
            v.setTag(holder);
        }
        holder = (ViewHolder)v.getTag();
        Bank bank = getData(position);
        holder.address.setText(bank.Address);
        holder.time.setText(bank.Time());
        holder.type.setText(bank.Type);
        holder.status.setText(bank.getStatusText());
        holder.status.setTextColor(bank.Status ? c.getResources().getColor(R.color.bankOn, null) : c.getResources().getColor(R.color.bankOff, null));

        return v;
    }

    Bank getData(int position){
        return (getItem(position));
    }

    private static class ViewHolder {
        private TextView address;
        private TextView time;
        private TextView type;
        private TextView status;
    }
}