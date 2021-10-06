package com.example.goldenfish.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goldenfish.Common.CommonInterface;
import com.example.goldenfish.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<ModelDashboard> courseDataArrayList;
    private Context mcontext;
CommonInterface commonInterface;
    public RecyclerViewAdapter(ArrayList<ModelDashboard> recyclerDataArrayList, Context mcontext,CommonInterface commonInterface) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        this.commonInterface=commonInterface;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dashboard, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        ModelDashboard recyclerData = courseDataArrayList.get(position);
        holder.title.setText(recyclerData.getTitle());
        holder.img.setImageResource(recyclerData.getImgid());

        holder.item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonInterface.dashboardClick(courseDataArrayList.get(position).getTitle());
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

        private TextView title;
        private ImageView img;
        private CardView item_click;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            item_click=itemView.findViewById(R.id.item_click);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
        }
    }
}
