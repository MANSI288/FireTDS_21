package com.example.firetds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TdsAdapter extends RecyclerView.Adapter<TdsAdapter.MyViewHolder> {

    Context context;
    ArrayList<TdsData> list;

    public TdsAdapter(Context context, ArrayList<TdsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TdsData tdsData = list.get(position);
        holder.ppm1.setText(String.valueOf(tdsData.getPpm1()));
        holder.date.setText(tdsData.getDate());
        holder.insight.setText(tdsData.getInsight());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, ppm1, insight, txt_option;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ppm1 = itemView.findViewById(R.id.ppmTextView);
            date = itemView.findViewById(R.id.dateTextView);
            insight = itemView.findViewById(R.id.insightText);
            txt_option = itemView.findViewById(R.id.txt_option);
        }
    }
}
