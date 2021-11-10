package com.example.goldenfish.AepsSdk.model;

public class AepsMiniModel {
    String amount;
    String date;
    String narration;
    String txnType;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public String getTxnType() {
        return this.txnType;
    }

    public void setTxnType(String txnType2) {
        this.txnType = txnType2;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount2) {
        this.amount = amount2;
    }

    public String getNarration() {
        return this.narration;
    }

    public void setNarration(String narration2) {
        this.narration = narration2;
    }
}
