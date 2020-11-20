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

/**
 * Application ListView
 * Created by EvILeg on 21.07.2015.
 */
public class banksAdapter extends BaseAdapter {

    /*
     * Создаем объекты для отображения внешнего вида элемента
     * и объекта списка, с которым будет производиться работа
     */
    private LayoutInflater LInflater;
    private ArrayList<Bank> listBanks;
    Context c;
    /*
     * Конструктор класса. В данном случае лишь транслируется лист с данными
     * в лист адаптера, с которым будет производиться непосредственная работа
     */
    public banksAdapter(Context context, ArrayList<Bank> data){
        c = context;
        listBanks = data;
        LInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*
     * Далее идут стандартные методы родительского класса BaseAdapter.
     * Внимательно ознакомьтесь с отличиями методов в уроке и методов,
     * которые автоматически создает Android Studio.
     * Данные методы должны работать непосредственно с используемым нами ArrayList
     * и структурой данных, формируемой классом DataFlags
     */
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

    /*
     * Метод, в котором формируется внешний вид элементов с его наполнением
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View v = convertView;

        /*
         * В том случае, если вид элемента не создан, производится его создание
         * с помощью ViewHolder и тегирование данного элемента конкретным holder объектом
         */
        if ( v == null){
            holder = new ViewHolder();
            v = LInflater.inflate(R.layout.bank_list_item, parent, false);
            holder.address = (TextView) v.findViewById(R.id.address);
            holder.time = ((TextView) v.findViewById(R.id.time));
            holder.type = ((TextView) v.findViewById(R.id.type));
            holder.status = ((TextView) v.findViewById(R.id.status));
            v.setTag(holder);
        }

        /*
         * После того, как все элементы определены, производится соотнесение
         * внешнего вида, данных и конкретной позиции в ListView.
         * После чего из ArrayList забираются данные для элемента ListView и
         * передаются во внешний вид элемента
         */
        holder = (ViewHolder)v.getTag();
        Bank bank = getData(position);
        holder.address.setText(bank.Address);
        holder.time.setText(bank.Time());
        holder.type.setText(bank.Type);
        holder.status.setText(bank.getStatusText());
        holder.status.setTextColor(bank.Status ? c.getResources().getColor(R.color.bankOn, null) : c.getResources().getColor(R.color.bankOff, null));

        return v;
    }

    /*
     * Метод, который забирает объект из ArrayList для дальнейшей работы с ним
     * и передачи его данных в элемент ListView
     */
    Bank getData(int position){
        return (getItem(position));
    }

    /*
     * Данная структура данных необходима для того, чтобы при пролистывании
     * большого списка не возникало артефактов и перескакивания данных с одной позиции ListView
     * на другую, что достигается тегированием каждого элемента ListView
     */
    private static class ViewHolder {
        private TextView address;
        private TextView time;
        private TextView type;
        private TextView status;
    }
}