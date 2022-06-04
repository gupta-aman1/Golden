package com.business.goldenfish.moneyTransfer.modeldmt

data class Remitter(
    val address: String,
    val city: String,
    val consumedlimit: Int,
    val id: String,
    val is_verified: Int,
    val kycdocs: String,
    val kycstatus: String,
    val mobile: String,
    val name: String,
    val perm_txn_limit: Int,
    val pincode: String,
    val remaininglimit: Int,
    val state: String
)