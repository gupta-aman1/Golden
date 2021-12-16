package com.business.goldenfish.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadFile {

    @SerializedName("Statuscode")
    @Expose
    private String statuscode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private String data;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}