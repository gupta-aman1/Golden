package com.business.goldenfish.moneyTransfer.modeldmt

data class Limit(
    val consumed: Int,
    val remaining: Int,
    val total: Int
)