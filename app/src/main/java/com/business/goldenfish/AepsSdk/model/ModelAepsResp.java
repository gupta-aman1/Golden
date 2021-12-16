package com.business.goldenfish.AepsSdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAepsResp {

    @Override
    public String toString() {
        return "ModelAepsResp{" +
                "statuscode='" + statuscode + '\'' +
                ", message='" + message + '\'' +
                ", head1='" + head1 + '\'' +
                ", head2='" + head2 + '\'' +
                ", head3='" + head3 + '\'' +
                ", data=" + data +
                '}';
    }

    @SerializedName("Statuscode")
    @Expose
    private String statuscode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Head1")
    @Expose
    private String head1;
    @SerializedName("Head2")
    @Expose
    private String head2;
    @SerializedName("Head3")
    @Expose
    private String head3;
    @SerializedName("Data")
    @Expose
    private List<ModelMakeAepsTran> data = null;

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

    public String getHead1() {
        return head1;
    }

    public void setHead1(String head1) {
        this.head1 = head1;
    }

    public String getHead2() {
        return head2;
    }

    public void setHead2(String head2) {
        this.head2 = head2;
    }

    public String getHead3() {
        return head3;
    }

    public void setHead3(String head3) {
        this.head3 = head3;
    }

    public List<ModelMakeAepsTran> getData() {
        return data;
    }

    public void setData(List<ModelMakeAepsTran> data) {
        this.data = data;
    }
}