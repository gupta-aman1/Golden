package com.example.goldenfish.sidebar.allReports.modelAllReports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllReportNew {

    @SerializedName("Operator Name")
    @Expose
    private String operatorName;
    @SerializedName("Order ID")
    @Expose
    private String orderID;
    @SerializedName("Aadhar Number")
    @Expose
    private String aadharNumber;
    @SerializedName("Mobile No")
    @Expose
    private String mobileNo;
    @SerializedName("Bank Name")
    @Expose
    private String bankName;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Commission")
    @Expose
    private String commission;
    @SerializedName("Total Txn Amount")
    @Expose
    private String totalTxnAmount;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Date & Time")
    @Expose
    private String dateTime;
    @SerializedName("Reason")
    @Expose
    private String reason;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getTotalTxnAmount() {
        return totalTxnAmount;
    }

    public void setTotalTxnAmount(String totalTxnAmount) {
        this.totalTxnAmount = totalTxnAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
