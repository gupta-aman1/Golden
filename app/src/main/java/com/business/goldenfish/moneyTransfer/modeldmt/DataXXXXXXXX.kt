package com.business.goldenfish.moneyTransfer.modeldmt

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataXXXXXXXX(
    val Amount: Double,
    @SerializedName("Bank Name")
    @Expose
    val BankName: String,
    @SerializedName("Beni Account")
    @Expose
    val BeniAccount: String,
    @SerializedName("Beni Name")
    @Expose
    val BeniName: String,
    @SerializedName("Date & Time")
    @Expose
    val DateTime: String,
    @SerializedName("IFSC Code")
    @Expose
    val IFSCCode: String,
    @SerializedName("Order Id")
    @Expose
    val OrderId: String,
    @SerializedName("Ref ID")
    @Expose
    val RefID: Int,
    @SerializedName("Remitter Mobile")
    @Expose
    val RemitterMobile: String,
    val Status: String,
    val Surcharge: String,
    @SerializedName("Transaction Id")
    @Expose
    val TransactionId: String
)