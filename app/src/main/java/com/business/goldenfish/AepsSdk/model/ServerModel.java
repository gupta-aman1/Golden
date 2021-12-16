package com.business.goldenfish.AepsSdk.model;


import com.business.goldenfish.AepsSdk.device.PidData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerModel {

    @SerializedName("Userid")
    @Expose
    private String userid;
    @SerializedName("checksum")
    @Expose
    private String checksum;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("AadhaarUid")
    @Expose
    private String aadhaarUid;
    @SerializedName("TransactionType")
    @Expose
    private String transactionType;
    @SerializedName("BankId")
    @Expose
    private String bankId;
    @SerializedName("BankIIN")
    @Expose
    private String bankIIN;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("PidDataType")
    @Expose
    private String pidDataType;

    @SerializedName("CI")
    @Expose
    private String ci;
    @SerializedName("DC")
    @Expose
    private String dc;
    @SerializedName("DpId")
    @Expose
    private String dpId;
    @SerializedName("ErrCode")
    @Expose
    private String errCode;
    @SerializedName("ErrInfo")
    @Expose
    private String errInfo;
    @SerializedName("FCount")
    @Expose
    private String fCount;
    @SerializedName("TType")
    @Expose
    private String tType;
    @SerializedName("HMac")
    @Expose
    private String hMac;
    @SerializedName("ICount")
    @Expose
    private String iCount;
    @SerializedName("MC")
    @Expose
    private String mc;
    @SerializedName("MI")
    @Expose
    private String mi;
    @SerializedName("NMPoints")
    @Expose
    private String nMPoints;
    @SerializedName("PCount")
    @Expose
    private String pCount;
    @SerializedName("PType")
    @Expose
    private String pType;
    @SerializedName("QScore")
    @Expose
    private String qScore;
    @SerializedName("RDSId")
    @Expose
    private String rDSId;
    @SerializedName("RDSVer")
    @Expose
    private String rDSVer;
    @SerializedName("SessionKey")
    @Expose
    private String sessionKey;


    @SerializedName("Srno")
    @Expose
    private String srno;

    private String PidData;

    public String getPidData() {
        return PidData;
    }

    public void setPidData(String pidData) {
        PidData = pidData;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAadhaarUid() {
        return aadhaarUid;
    }

    public void setAadhaarUid(String aadhaarUid) {
        this.aadhaarUid = aadhaarUid;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankIIN() {
        return bankIIN;
    }

    public void setBankIIN(String bankIIN) {
        this.bankIIN = bankIIN;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPidDataType() {
        return pidDataType;
    }

    public void setPidDataType(String pidDataType) {
        this.pidDataType = pidDataType;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getfCount() {
        return fCount;
    }

    public void setfCount(String fCount) {
        this.fCount = fCount;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public String gethMac() {
        return hMac;
    }

    public void sethMac(String hMac) {
        this.hMac = hMac;
    }

    public String getiCount() {
        return iCount;
    }

    public void setiCount(String iCount) {
        this.iCount = iCount;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getnMPoints() {
        return nMPoints;
    }

    public void setnMPoints(String nMPoints) {
        this.nMPoints = nMPoints;
    }

    public String getpCount() {
        return pCount;
    }

    public void setpCount(String pCount) {
        this.pCount = pCount;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getqScore() {
        return qScore;
    }

    public void setqScore(String qScore) {
        this.qScore = qScore;
    }

    public String getrDSId() {
        return rDSId;
    }

    public void setrDSId(String rDSId) {
        this.rDSId = rDSId;
    }

    public String getrDSVer() {
        return rDSVer;
    }

    public void setrDSVer(String rDSVer) {
        this.rDSVer = rDSVer;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

}