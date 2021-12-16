package com.business.goldenfish.AepsSdk.model;

public class ModelMiniData {
    String date;
    String txnType;
    String amount;

    @Override
    public String toString() {
        return "ModelMiniData{" +
                "date='" + date + '\'' +
                ", txnType='" + txnType + '\'' +
                ", amount='" + amount + '\'' +
                ", narration='" + narration + '\'' +
                '}';
    }

    public ModelMiniData(String date, String txnType, String amount, String narration) {
        this.date = date;
        this.txnType = txnType;
        this.amount = amount;
        this.narration = narration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    String narration;
}
