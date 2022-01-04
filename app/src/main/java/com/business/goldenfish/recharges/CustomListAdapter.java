package com.business.goldenfish.recharges;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.business.goldenfish.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Model_Class_For_Prepaid_Operator> {
    Context context;
    DrawerItemHolder drawerHolder;

    public CustomListAdapter(Context context, int resource, ArrayList<Model_Class_For_Prepaid_Operator> itemsList) {
        super(context, resource, itemsList);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        try {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                drawerHolder = new DrawerItemHolder();
                view = inflater.inflate(R.layout.operator_view_layout,
                        viewGroup, false);
                drawerHolder.name = (TextView) view.findViewById(R.id.opname);
                drawerHolder.imgIcon = (ImageView) view.findViewById(R.id.thumbnail);
                view.setTag(drawerHolder);
            } else {
                drawerHolder = (DrawerItemHolder) view.getTag();
            }
            Model_Class_For_Prepaid_Operator model = getItem(position);
            drawerHolder.name.setText(model.getOperatorName());
            Picasso.with(getContext())
                    .load(model.getImage())
                    .into(drawerHolder.imgIcon);
        }catch (Exception e){

        }

        return view;
    }

    private class DrawerItemHolder
    {
        TextView name;
        ImageView imgIcon;
    }
}



