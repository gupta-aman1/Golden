package com.business.goldenfish.moneyTransfer.modeldmt

data class DataX(
    val beneficiary: List<Beneficiary>,
    val remitter: Remitter,
    val remitter_limit: List<RemitterLimit>
)