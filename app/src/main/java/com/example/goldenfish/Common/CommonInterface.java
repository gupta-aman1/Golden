package com.example.goldenfish.Common;

import com.example.goldenfish.Sidebar.AllReports.AllReportAdapterNew;

public interface CommonInterface {

    default void getMoveToBankSurcharge(String CommPer,String CommType,String ChargePer,String ChargeType)
    {

    }

    default void getWalletBalance(String bal)
    {

    }
    default void dashboardClick(String val)
    {

    }

    default void requestPermission(boolean status)
    {

    }

    default void requestPermission1(boolean status, AllReportAdapterNew.ViewHolder viewHolder)
    {

    }
}
