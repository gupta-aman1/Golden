package com.business.goldenfish.moneyTransfer.modeldmt

data class RemitterLimit(
    val code: Int,
    val limit: Limit,
    val mode: Mode,
    val status: Int
)