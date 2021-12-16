package com.business.goldenfish.AepsSdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AepsModelRequest {
    @SerializedName("request")
    @Expose
    private Requestss request;

    public Requestss getRequest() {
        return this.request;
    }

    public void setRequest(Requestss request2) {
        this.request = request2;
    }

    @Override
    public String toString() {
        return "AepsModelRequest{" +
                "request=" + request +
                '}';
    }

    public static class Requestss {
        @SerializedName("aadharno")
        @Expose
        private String aadharno;

        @Override
        public String toString() {
            return "Requestss{" +
                    "aadharno='" + aadharno + '\'' +
                    ", amount='" + amount + '\'' +
                    ", bankiin='" + bankiin + '\'' +
                    ", bankname='" + bankname + '\'' +
                    ", data=" + data +
                    ", mobileno='" + mobileno + '\'' +
                    ", spkey='" + spkey + '\'' +
                    '}';
        }

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("bankiin")
        @Expose
        private String bankiin;
        @SerializedName("bankname")
        @Expose
        private String bankname;
        @SerializedName("data")
        @Expose
        private Datass data;
        @SerializedName("mobileno")
        @Expose
        private String mobileno;
        @SerializedName("spkey")
        @Expose
        private String spkey;

        public String getBankiin() {
            return this.bankiin;
        }

        public void setBankiin(String bankiin2) {
            this.bankiin = bankiin2;
        }

        public String getBankname() {
            return this.bankname;
        }

        public void setBankname(String bankname2) {
            this.bankname = bankname2;
        }

        public String getAadharno() {
            return this.aadharno;
        }

        public void setAadharno(String aadharno2) {
            this.aadharno = aadharno2;
        }

        public String getMobileno() {
            return this.mobileno;
        }

        public void setMobileno(String mobileno2) {
            this.mobileno = mobileno2;
        }

        public String getSpkey() {
            return this.spkey;
        }

        public void setSpkey(String spkey2) {
            this.spkey = spkey2;
        }

        public String getAmount() {
            return this.amount;
        }

        public void setAmount(String amount2) {
            this.amount = amount2;
        }

        public Datass getData() {
            return this.data;
        }

        public void setData(Datass data2) {
            this.data = data2;
        }
    }

    public static class Datass {
        @SerializedName("ci")
        @Expose
        private String ci;
        @SerializedName("dc")
        @Expose
        private String dc;

        @Override
        public String toString() {
            return "Datass{" +
                    "ci='" + ci + '\'' +
                    ", dc='" + dc + '\'' +
                    ", dpid='" + dpid + '\'' +
                    ", errcode='" + errcode + '\'' +
                    ", errinfo='" + errinfo + '\'' +
                    ", fcount='" + fcount + '\'' +
                    ", hmac='" + hmac + '\'' +
                    ", mc='" + mc + '\'' +
                    ", mi='" + mi + '\'' +
                    ", nmpoints='" + nmpoints + '\'' +
                    ", piddatavalue='" + piddatavalue + '\'' +
                    ", qscore='" + qscore + '\'' +
                    ", rdsid='" + rdsid + '\'' +
                    ", rdsver='" + rdsver + '\'' +
                    ", sessionKey='" + sessionKey + '\'' +
                    ", srno='" + srno + '\'' +
                    '}';
        }

        @SerializedName("dpid")
        @Expose
        private String dpid;
        @SerializedName("errcode")
        @Expose
        private String errcode;
        @SerializedName("errinfo")
        @Expose
        private String errinfo;
        @SerializedName("fcount")
        @Expose
        private String fcount;
        @SerializedName("hmac")
        @Expose
        private String hmac;
        @SerializedName("mc")
        @Expose
        private String mc;
        @SerializedName("mi")
        @Expose
        private String mi;
        @SerializedName("nmpoints")
        @Expose
        private String nmpoints;
        @SerializedName("piddatavalue")
        @Expose
        private String piddatavalue;
        @SerializedName("qscore")
        @Expose
        private String qscore;
        @SerializedName("rdsid")
        @Expose
        private String rdsid;
        @SerializedName("rdsver")
        @Expose
        private String rdsver;
        @SerializedName("sessionKey")
        @Expose
        private String sessionKey;
        @SerializedName("srno")
        @Expose
        private String srno;

        public String getSrno() {
            return this.srno;
        }

        public void setSrno(String srno2) {
            this.srno = srno2;
        }

        public String getPiddatavalue() {
            return this.piddatavalue;
        }

        public void setPiddatavalue(String piddatavalue2) {
            this.piddatavalue = piddatavalue2;
        }

        public String getCi() {
            return this.ci;
        }

        public void setCi(String ci2) {
            this.ci = ci2;
        }

        public String getDc() {
            return this.dc;
        }

        public void setDc(String dc2) {
            this.dc = dc2;
        }

        public String getDpid() {
            return this.dpid;
        }

        public void setDpid(String dpid2) {
            this.dpid = dpid2;
        }

        public String getErrcode() {
            return this.errcode;
        }

        public void setErrcode(String errcode2) {
            this.errcode = errcode2;
        }

        public String getErrinfo() {
            return this.errinfo;
        }

        public void setErrinfo(String errinfo2) {
            this.errinfo = errinfo2;
        }

        public String getFcount() {
            return this.fcount;
        }

        public void setFcount(String fcount2) {
            this.fcount = fcount2;
        }

        public String getHmac() {
            return this.hmac;
        }

        public void setHmac(String hmac2) {
            this.hmac = hmac2;
        }

        public String getMc() {
            return this.mc;
        }

        public void setMc(String mc2) {
            this.mc = mc2;
        }

        public String getMi() {
            return this.mi;
        }

        public void setMi(String mi2) {
            this.mi = mi2;
        }

        public String getNmpoints() {
            return this.nmpoints;
        }

        public void setNmpoints(String nmpoints2) {
            this.nmpoints = nmpoints2;
        }

        public String getQscore() {
            return this.qscore;
        }

        public void setQscore(String qscore2) {
            this.qscore = qscore2;
        }

        public String getRdsid() {
            return this.rdsid;
        }

        public void setRdsid(String rdsid2) {
            this.rdsid = rdsid2;
        }

        public String getRdsver() {
            return this.rdsver;
        }

        public void setRdsver(String rdsver2) {
            this.rdsver = rdsver2;
        }

        public String getSessionKey() {
            return this.sessionKey;
        }

        public void setSessionKey(String sessionKey2) {
            this.sessionKey = sessionKey2;
        }
    }
}
