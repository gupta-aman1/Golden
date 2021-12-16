package com.business.goldenfish.AepsSdk.model;

public class BankModel {
    String IIN;
    String Id;

    public String getIIN() {
        return IIN;
    }

    @Override
    public String toString() {
        return "BankModel{" +
                "IIN='" + IIN + '\'' +
                ", Id='" + Id + '\'' +
                '}';
    }

    public BankModel(String IIN, String id) {
        this.IIN = IIN;
        Id = id;
    }

    public void setIIN(String IIN) {
        this.IIN = IIN;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
