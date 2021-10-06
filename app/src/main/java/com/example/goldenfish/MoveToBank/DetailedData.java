package com.example.goldenfish.MoveToBank;

public class DetailedData {

    String  key,value;

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "DetailedData{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public DetailedData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
