package com.business.goldenfish.moneyTransfer.modeldmt

import java.util.*

data class Data(
    val `data`: Any,
    val environment: String,
    val ipay_uuid: String,
    val orderid: String,
    val status: String,
    val statuscode: String,
    val timestamp: String
)