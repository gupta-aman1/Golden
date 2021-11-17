package com.example.goldenfish.AepsSdk.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMakeAepsTran {

    @Override
    public String toString() {
        return "ModelMakeAepsTran{" +
                "id=" + id +
                ", txntype='" + txntype + '\'' +
                ", bcId='" + bcId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", opname='" + opname + '\'' +
                ", orderId='" + orderId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", amount='" + amount + '\'' +
                ", commission='" + commission + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", miniSmt='" + miniSmt + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", reqDate='" + reqDate + '\'' +
                '}';
    }

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("Txntype")
    @Expose
    private String txntype;
    @SerializedName("BcId")
    @Expose
    private String bcId;
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @SerializedName("opname")
    @Expose
    private String opname;
    @SerializedName("OrderId")
    @Expose
    private String orderId;
    @SerializedName("TransactionId")
    @Expose
    private String transactionId;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Commission")
    @Expose
    private String commission;
    @SerializedName("Mobileno")
    @Expose
    private String mobileno;
    @SerializedName("AadharNumber")
    @Expose
    private String aadharNumber;
    @SerializedName("MiniSmt")
    @Expose
    private String miniSmt;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ReqDate")
    @Expose
    private String reqDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }

    public String getBcId() {
        return bcId;
    }

    public void setBcId(String bcId) {
        this.bcId = bcId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getMiniSmt() {
        return miniSmt;
    }

    public void setMiniSmt(String miniSmt) {
        this.miniSmt = miniSmt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }


}