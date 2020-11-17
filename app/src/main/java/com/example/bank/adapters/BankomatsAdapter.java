package com.example.bank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.models.Bankomat;
import com.example.bank.R;
import com.example.bank.models.Bankomat;

public class BankomatsAdapter extends RecyclerView.Adapter<com.example.bank.adapters.BankomatsAdapter.ViewHolder> {
    Context mContext;
    Bankomat[] mBankomats;
    public BankomatsAdapter(Context context, Bankomat[] bankomats){
        mContext = context;
        mBankomats = bankomats;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.bankomat_work,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bankomat bankomat = mBankomats[position];
        holder.address.setText(bankomat.getAddress());
        holder.status.setText(bankomat.getStatusText());
        holder.timings.setText(bankomat.getTimings());
        if(bankomat.getStatus()) {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.bankomat_on));
        }else{
            holder.status.setTextColor(mContext.getResources().getColor(R.color.bankomat_off));
        }
    }

    @Override
    public int getItemCount() {
        return mBankomats.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView address;
        TextView status;
        TextView timings;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.bankomat_address);
            status = itemView.findViewById(R.id.bankomat_status);
            timings = itemView.findViewById(R.id.bankomat_timings);
        }
    }
}
