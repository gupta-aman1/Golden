package com.business.goldenfish.Utilities;

public interface OnDataReceiverListener {
    void onDataReceived(String myData, Double latitude, Double longitude,String address);
}