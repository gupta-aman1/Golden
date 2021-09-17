package com.example.goldenfish.Sidebar.AllReports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goldenfish.R;
import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.AllReport;

import java.util.ArrayList;

public class AllReportAdapterNew extends RecyclerView.Adapter<AllReportAdapterNew.ViewHolder>{
    Context context;
    private ArrayList<AllReport> filteredData = null;

    public AllReportAdapterNew(Activity context, ArrayList<AllReport> data) {
        this.context = context;
        this.filteredData = data;
        System.out.println("FULL RESPONSE "+filteredData);
    }

    @Override
    public AllReportAdapterNew.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_allreports, parent, false);
        AllReportAdapterNew.ViewHolder viewHolder = new AllReportAdapterNew.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllReportAdapterNew.ViewHolder holder, int position) {
        AllReport myListData = filteredData.get(position);
        holder.book_date_bus.setText(filteredData.get(position).getAgentname());
        System.out.println("FULL RESPONSE1 "+filteredData.get(position).getAgentname());
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView book_date_bus,book_time_bus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // book_time_bus = itemView.findViewById(R.id.book_time_bus);
            book_date_bus = itemView.findViewById(R.id.book_date_bus);

        }
    }
}
