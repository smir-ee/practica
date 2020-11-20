package com.example.praktika;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.praktika.R;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private List<ListItemClass> listItem = new ArrayList<>();
    private Context context;


    public CustomArrayAdapter(Context context, int resource, List<ListItemClass> listItem, LayoutInflater inflater)
    {
        super(context, resource, listItem);
        this.inflater = inflater;
        this.listItem = listItem;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ViewHolder viewHolder;
        ListItemClass listItemMain = listItem.get(position);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_course, null, false);
            viewHolder = new ViewHolder();
            viewHolder.data1 = convertView.findViewById(R.id.valute);
            viewHolder.data2 = convertView.findViewById(R.id.valutefull);
            viewHolder.data3 = convertView.findViewById(R.id.textView_Sale_Course);
            viewHolder.data4 = convertView.findViewById(R.id.textView_Buy_Course);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.data1.setText(listItemMain.getData1());
        viewHolder.data2.setText(listItemMain.getData2());
        viewHolder.data3.setText(listItemMain.getData3());
        viewHolder.data4.setText(listItemMain.getData4());

        return convertView;
    }
    private class ViewHolder{
        TextView data1;
        TextView data2;
        TextView data3;
        TextView data4;
    }

}