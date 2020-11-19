package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater inflater;
    private List<ListItem> listItems = new ArrayList<>();

    public CustomArrayAdapter(@NonNull Context context, int resource, List<ListItem> listItems, LayoutInflater inflater){
        super(context, resource, listItems);
        this.inflater = inflater;
        this.listItems = listItems;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ViewHolder viewHolder;
        ListItem listItem = listItems.get(position);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.curs_maket, null, false);
            viewHolder = new ViewHolder();
            viewHolder.data1 = convertView.findViewById(R.id.valute);
            viewHolder.data2 = convertView.findViewById(R.id.valuteFull);
            viewHolder.data3 = convertView.findViewById(R.id.costUp);
            viewHolder.data4 = convertView.findViewById(R.id.costDown);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.data1.setText(listItem.getData1());
        viewHolder.data2.setText(listItem.getData2());
        viewHolder.data3.setText(listItem.getData3());
        viewHolder.data4.setText(listItem.getData4());

        return convertView;
    }
    private class ViewHolder{
        TextView data1;
        TextView data2;
        TextView data3;
        TextView data4;
    }

}
