package com.business.goldenfish.sidebar.allReports;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.business.goldenfish.Common.CommonInterface;
import com.business.goldenfish.Constants.Constant;
import com.business.goldenfish.R;
import com.business.goldenfish.sidebar.allReports.modelAllReports.ModelServiceData;

import java.util.ArrayList;

class AdapterGetServies extends RecyclerView.Adapter<AdapterGetServies.RecyclerViewHolder> {

    private ArrayList<ModelServiceData> courseDataArrayList;
    private Context mcontext;
    CommonInterface commonInterface;
    public AdapterGetServies(ArrayList<ModelServiceData> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
       // this.commonInterface=commonInterface;
    }

    @NonNull
    @Override
    public AdapterGetServies.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_services, parent, false);
        return new AdapterGetServies.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGetServies.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        ModelServiceData recyclerData = courseDataArrayList.get(position);
        holder.text.setText(recyclerData.getServiceName());
       // holder.img.setImageResource(recyclerData.getImgid());

        holder.clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mcontext.startActivity(new Intent(mcontext,AllReportsActivity.class)
                .putExtra(Constant.service_id,courseDataArrayList.get(position).getServiceId()));
               // commonInterface.dashboardClick(courseDataArrayList.get(position).getServiceId());
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView img;
        private CardView item_click;
        RelativeLayout clickLayout;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
         //   item_click=itemView.findViewById(R.id.item_click);
            text = itemView.findViewById(R.id.text);
            clickLayout=itemView.findViewById(R.id.clickLayout);
            //img = itemView.findViewById(R.id.img);
        }
    }
}
