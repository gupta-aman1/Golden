package com.business.goldenfish.Common;

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
    default void OTPtatus(boolean status,String ref,String hash)
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
