package com.example.goldenfish.Sidebar.AllReports.ModelAllReports;

public class AllReport {

    String Agentname;
    String Stype;
    String amount;

    public AllReport(String agentname, String stype, String amount) {
        Agentname = agentname;
        Stype = stype;
        this.amount = amount;
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


