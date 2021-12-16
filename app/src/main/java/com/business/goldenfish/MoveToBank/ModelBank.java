package com.business.goldenfish.MoveToBank;

public class ModelBank {
    String bankid;
    String ifsc;

    public ModelBank(String bankid, String ifsc) {
        this.bankid = bankid;
        this.ifsc = ifsc;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
}
