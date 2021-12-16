package com.business.goldenfish.AepsSdk.AepsAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.business.goldenfish.AepsSdk.model.ModelMiniData;
import com.business.goldenfish.Common.CommonInterface;
import com.business.goldenfish.Dashboard.ModelDashboard;
import com.business.goldenfish.R;

import java.util.ArrayList;

public class AdapterMini extends RecyclerView.Adapter<AdapterMini.RecyclerViewHolder> {

    private ArrayList<ModelMiniData> courseDataArrayList;
    private Context mcontext;
   // CommonInterface commonInterface;
    public AdapterMini(ArrayList<ModelMiniData> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
       // this.commonInterface=commonInterface;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mini_data, parent, false);
        return new AdapterMini.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMini.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        ModelMiniData recyclerData = courseDataArrayList.get(position);
        holder.date.setText(recyclerData.getDate());
        holder.txn_type.setText(recyclerData.getTxnType());
        holder.amount.setText(recyclerData.getAmount());
        holder.narration.setText(recyclerData.getNarration());

    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView date,narration,amount,txn_type;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
          //  item_click=itemView.findViewById(R.id.item_click);
            date = itemView.findViewById(R.id.date);
            narration = itemView.findViewById(R.id.narration);
            amount = itemView.findViewById(R.id.amount);
            txn_type = itemView.findViewById(R.id.txn_type);
            //img = itemView.findViewById(R.id.img);
        }
    }
}
