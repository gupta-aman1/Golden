package com.business.goldenfish.ledgerreopt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ModelDataLedger {
        @SerializedName("Balance Id")
        @Expose
        private Integer balanceId;
        @SerializedName("OrderId")
        @Expose
        private String orderId;
        @SerializedName("Service Name")
        @Expose
        private String serviceName;
        @SerializedName("Refrence Id")
        @Expose
        private Integer refrenceId;
        @SerializedName("Account")
        @Expose
        private String account;
        @SerializedName("Order Value")
        @Expose
        private Double orderValue;
        @SerializedName("Amount")
        @Expose
        private Double amount;
        @SerializedName("Commission")
        @Expose
        private Double commission;
        @SerializedName("Surcharge")
        @Expose
        private Object surcharge;
        @SerializedName("Cr/Dr")
        @Expose
        private String crDr;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Opening Bal")
        @Expose
        private Double openingBal;
        @SerializedName("Closing Bal")
        @Expose
        private Double closingBal;
        @SerializedName("Remarks")
        @Expose
        private String remarks;
        @SerializedName("Date & Time")
        @Expose
        private String dateTime;

        public Integer getBalanceId() {
            return balanceId;
        }

        public void setBalanceId(Integer balanceId) {
            this.balanceId = balanceId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public Integer getRefrenceId() {
            return refrenceId;
        }

        public void setRefrenceId(Integer refrenceId) {
            this.refrenceId = refrenceId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Double getOrderValue() {
            return orderValue;
        }

        public void setOrderValue(Double orderValue) {
            this.orderValue = orderValue;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Double getCommission() {
            return commission;
        }

        public void setCommission(Double commission) {
            this.commission = commission;
        }

        public Object getSurcharge() {
            return surcharge;
        }

        public void setSurcharge(Object surcharge) {
            this.surcharge = surcharge;
        }

        public String getCrDr() {
            return crDr;
        }

        public void setCrDr(String crDr) {
            this.crDr = crDr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Double getOpeningBal() {
            return openingBal;
        }

        public void setOpeningBal(Double openingBal) {
            this.openingBal = openingBal;
        }

    @Override
    public String toString() {
        return "ModelDataLedger{" +
                "balanceId=" + balanceId +
                ", orderId='" + orderId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", refrenceId=" + refrenceId +
                ", account='" + account + '\'' +
                ", orderValue=" + orderValue +
                ", amount=" + amount +
                ", commission=" + commission +
                ", surcharge=" + surcharge +
                ", crDr='" + crDr + '\'' +
                ", status='" + status + '\'' +
                ", openingBal=" + openingBal +
                ", closingBal=" + closingBal +
                ", remarks='" + remarks + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    public Double getClosingBal() {
            return closingBal;
        }

        public void setClosingBal(Double closingBal) {
            this.closingBal = closingBal;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }


}
