package com.example.goldenfish.sidebar.allReports.modelAllReports;

import java.util.List;

public class ModelMainNew {
    String Statuscode;
    String Message;
    List<AllReportNew> Data;

    @Override
    public String toString() {
        return "ModelMainNew{" +
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

    public List<AllReportNew> getData() {
        return Data;
    }

    public void setData(List<AllReportNew> data) {
        Data = data;
    }
}
