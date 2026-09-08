package com.abrarshakhi.denapawna.domain.model

data class ParsedTransaction(
    val amount: Double,
    val merchant: String,
    val currency: String,
    val isIncome: Boolean
)
