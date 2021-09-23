package com.example.goldenfish.Sidebar.AllReports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.goldenfish.R;
import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.AllReport;
import com.example.goldenfish.Sidebar.AllReports.ModelAllReports.AllReportsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AllReportFragment extends Fragment {
    int myval;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ArrayList<AllReport> listItems = new ArrayList<AllReport>();
    private AllReportAdapterNew allReportsAdapter;
    RecyclerView credit_Report_listview;
    private String name, amount, Stype;

    public AllReportFragment(int value) {
        // Required empty public constructor
        myval=value;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_all_report, container, false);
        credit_Report_listview=v.findViewById(R.id.credit_Report_listview);
        String plan_type=  ((AllReportsActivity)getActivity()).allReportsHead.get(myval);
        List<AllReport> allLists=  ((AllReportsActivity)getActivity()).allReports;

                    for(int i=0;i<allLists.size();i++)
                    {
                       if(allLists.get(i).getStype().equalsIgnoreCase(plan_type))
                        {
                             name= allLists.get(i).getAgentname();
                             amount= allLists.get(i).getAmount();
                             Stype= allLists.get(i).getStype();
                             String txnid= allLists.get(i).getTransactionid();
                             String opname= allLists.get(i).getOpname();
                             String number= allLists.get(i).getNumber();
                             String comm= allLists.get(i).getComm();
                             String cost= allLists.get(i).getCost();
                             String bal = allLists.get(i).getBalance();
                             String datetime = allLists.get(i).getTdatetime();
                             String status = allLists.get(i).getStatus();
                            listItems.add(new AllReport(name,Stype,amount,txnid,opname,number,comm,cost,bal,datetime,status));
                       }
                        //System.out.println("FULL RESPONSE1 "+name);


                    }

        allReportsAdapter = new AllReportAdapterNew(getActivity(),listItems);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        credit_Report_listview.setLayoutManager(verticalLayoutManager);
       // allReportsAdapter.notifyDataSetChanged();
        credit_Report_listview.setAdapter(allReportsAdapter);
        return v;
    }
}