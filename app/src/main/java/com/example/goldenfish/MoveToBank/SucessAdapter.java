package com.example.goldenfish.MoveToBank;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goldenfish.R;


import java.util.ArrayList;

public class SucessAdapter extends RecyclerView.Adapter<SucessAdapter.ViewHolder> {

    ArrayList<DetailedData> arrayList;
    Context context;

    public SucessAdapter(ArrayList<DetailedData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_success, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        // holder.Image.setImageResource(arrayList.get(position).getName());
        String keyy=arrayList.get(position).getKey();
        String valuee=arrayList.get(position).getValue();
        if(!keyy.equalsIgnoreCase("MiniSmt"))
        {
            holder.key.setText(keyy);
            holder.value.setText(valuee);
        }
        else
        {
            holder.linear_main.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView value,key;
        LinearLayout linear_main;
        // TextView fileName,imaegSize;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linear_main=itemView.findViewById(R.id.linear_main);
            value = itemView.findViewById(R.id.value);
            key = itemView.findViewById(R.id.key);
        }
    }
}
