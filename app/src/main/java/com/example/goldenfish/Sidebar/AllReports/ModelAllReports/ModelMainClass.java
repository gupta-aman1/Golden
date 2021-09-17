package com.example.goldenfish.Sidebar.AllReports.ModelAllReports;

import java.util.List;

public class ModelMainClass {
    String Statuscode;
    String Message;
    List<AllReport> Data;

    public String getStatuscode() {
        return Statuscode;
    }

    @Override
    public String toString() {
        return "ModelMainClass{" +
                "Statuscode='" + Statuscode + '\'' +
                ", Message='" + Message + '\'' +
                ", Data=" + Data +
                '}';
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

    public List<AllReport> getData() {
        return Data;
    }

    public void setData(List<AllReport> data) {
        Data = data;
    }

    public ModelMainClass(String statuscode, String message, List<AllReport> data) {
        Statuscode = statuscode;
        Message = message;
        Data = data;
    }
}
