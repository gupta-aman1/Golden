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
       // System.out.println("FULL RESPONSE "+filteredData);
    }

    @Override
    public AllReportAdapterNew.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_all_recharge_list, parent, false);
        AllReportAdapterNew.ViewHolder viewHolder = new AllReportAdapterNew.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllReportAdapterNew.ViewHolder holder, int position) {
        AllReport myListData = filteredData.get(position);
        holder.tv_all_report_transaction_id.setText(filteredData.get(position).getTransactionid());
        holder.tv_all_report_operator_name.setText(filteredData.get(position).getOpname());
        holder.tv_all_report_number.setText(filteredData.get(position).getNumber());
        holder.tv_all_report_amount.setText(filteredData.get(position).getAmount());
        holder.tv_all_report_commission.setText(filteredData.get(position).getComm());
        holder.tv_all_report_cost.setText(filteredData.get(position).getCost());
        holder.tv_all_report_balance.setText(filteredData.get(position).getBalance());
        holder.tv_all_report_date_time.setText(filteredData.get(position).getTdatetime());
        holder.tv_all_report_status.setText(filteredData.get(position).getStatus());
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
        TextView tv_all_report_transaction_id,tv_all_report_operator_name,tv_all_report_number,tv_all_report_amount,
                tv_all_report_commission,tv_all_report_cost,tv_all_report_balance,tv_all_report_date_time,tv_all_report_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // book_time_bus = itemView.findViewById(R.id.book_time_bus);
            tv_all_report_transaction_id = itemView.findViewById(R.id.tv_all_report_transaction_id);
            tv_all_report_operator_name = itemView.findViewById(R.id.tv_all_report_operator_name);
            tv_all_report_number = itemView.findViewById(R.id.tv_all_report_number);
            tv_all_report_amount = itemView.findViewById(R.id.tv_all_report_amount);
            tv_all_report_commission = itemView.findViewById(R.id.tv_all_report_commission);
            tv_all_report_cost = itemView.findViewById(R.id.tv_all_report_cost);
            tv_all_report_balance = itemView.findViewById(R.id.tv_all_report_balance);
            tv_all_report_date_time = itemView.findViewById(R.id.tv_all_report_date_time);
            tv_all_report_status = itemView.findViewById(R.id.tv_all_report_status);

        }
    }
}
