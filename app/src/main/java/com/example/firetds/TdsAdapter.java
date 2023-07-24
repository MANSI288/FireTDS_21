package com.example.firetds;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

        holder.ppm1.setText(String.valueOf(tdsData.ppm1));
        holder.date.setText(tdsData.getDate());
        holder.insight.setText(tdsData.getInsight());

        holder.txt_option.setOnClickListener(v ->
        {
            PopupMenu popupMenu = new PopupMenu(context,holder.txt_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item ->
            {
                if (item.getItemId() == R.id.menu_remove) {
                    DAOTds dao = new DAOTds();
                    dao.remove(tdsData.getKey()).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
                        list.remove(tdsData);

                        Intent intent = new Intent(holder.txt_option.getContext(), HistoryList.class);
                        holder.txt_option.getContext().startActivity(intent);



                    });
                }

               return  false;

            });
            popupMenu.show();

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView date, ppm1, insight, txt_option;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

           // date = itemView.findViewById(R.id.dateTextView);
            ppm1 = itemView.findViewById(R.id.ppmTextView);
            date = itemView.findViewById(R.id.dateTextView);
            insight = itemView.findViewById(R.id.insightText);

            txt_option = itemView.findViewById(R.id.txt_option);

        }
    }
}


