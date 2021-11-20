package com.example.goldenfish.Common;

import com.example.goldenfish.Sidebar.AllReports.AllReportAdapterNew;

import java.util.ArrayList;

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

    default void walletBal(String status)
    {

    }
    default void MpinStatus(boolean status)
    {

    }
    default void OTPtatus(boolean status)
    {

    }
    default void getEnteredOTP(String otp)
    {

    }
    default void getStates(ArrayList<String> name,ArrayList<String> id)
    {

    }

    default void getCity(ArrayList<String> name,ArrayList<String> id)
    {

    }

}
