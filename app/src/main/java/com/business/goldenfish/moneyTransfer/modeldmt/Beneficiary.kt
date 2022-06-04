package com.business.goldenfish.moneyTransfer.modeldmt

data class Beneficiary(
    val account: String,
    val bank: String,
    val id: String,
    val ifsc: String,
    val imps: String,
    val last_success_date: String,
    val last_success_imps: String,
    val last_success_name: String,
    val mobile: String,
    val name: String,
    val status: String
)