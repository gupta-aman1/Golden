package com.example.goldenfish.PanCard.ModelPan;

import java.util.ArrayList;

public class ModelCouponPan {
    String Statuscode;

    @Override
    public String toString() {
        return "ModelCouponPan{" +
                "Statuscode='" + Statuscode + '\'' +
                ", Message='" + Message + '\'' +
                ", Data=" + Data +
                '}';
    }

    public String getStatuscode() {
        return Statuscode;
    }

    public void setStatuscode(String statuscode) {
        Statuscode = statuscode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ModelPanType> getData() {
        return Data;
    }

    public void setData(ArrayList<ModelPanType> data) {
        Data = data;
    }

    String Message;
    ArrayList<ModelPanType> Data;
}
