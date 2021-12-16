package com.business.goldenfish.sidebar.allReports.modelAllReports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.business.goldenfish.R;

import java.util.ArrayList;

public class AllReportsAdapter extends ArrayAdapter<AllReport> {


    Context context;
    DrawerItemHolder drawerHolder;

    public AllReportsAdapter(Context context, int resource, ArrayList<AllReport> itemsList) {
        super(context, resource, itemsList);

    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        try {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                drawerHolder = new DrawerItemHolder();
                view = inflater.inflate(R.layout.layout_allreports,
                        viewGroup, false);

                drawerHolder.book_date_bus = (TextView) view.findViewById(R.id.book_date_bus);
               // drawerHolder.book_time_bus = (TextView) view.findViewById(R.id.book_time_bus);
               // drawerHolder.validity = (TextView) view.findViewById(R.id.validity);

                view.setTag(drawerHolder);
            } else {
                drawerHolder = (DrawerItemHolder) view.getTag();
            }
            AllReport model = getItem(position);
            drawerHolder.book_date_bus.setText(model.getAgentname());
            drawerHolder.book_time_bus.setText(model.getAmount());
            System.out.println("FULL RESPONSE1 "+model.getAgentname());
            //drawerHolder.validity.setText(model.getValidity());
        }catch (Exception e){

        }

        return view;
    }

    private class DrawerItemHolder
    {
        TextView book_date_bus;
        TextView book_time_bus;
    }
}
