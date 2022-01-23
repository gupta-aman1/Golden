package com.example.goldenfish.sidebar.allReports.modelAllReports;

public class AllReport {

    String Agentname;
    String Stype;
    String amount;
    String transactionid;
    String opname;
    String number;
    String comm;
    String cost;
    String balance;
    String tdatetime;

    public AllReport(String agentname, String stype, String amount, String transactionid, String opname, String number, String comm, String cost, String balance, String tdatetime, String status) {
        Agentname = agentname;
        Stype = stype;
        this.amount = amount;
        this.transactionid = transactionid;
        this.opname = opname;
        this.number = number;
        this.comm = comm;
        this.cost = cost;
        this.balance = balance;
        this.tdatetime = tdatetime;
        this.status = status;
    }

    String status;

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTdatetime() {
        return tdatetime;
    }

    public void setTdatetime(String tdatetime) {
        this.tdatetime = tdatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "AllReport{" +
                "Agentname='" + Agentname + '\'' +
                ", Stype='" + Stype + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public String getAgentname() {
        return Agentname;
    }

    public void setAgentname(String agentname) {
        Agentname = agentname;
    }

    public String getStype() {
        return Stype;
    }

    public void setStype(String stype) {
        Stype = stype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}


