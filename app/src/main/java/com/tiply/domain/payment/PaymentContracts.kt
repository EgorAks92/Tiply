package com.tiply.domain.payment

import com.tiply.domain.model.CurrencyCode

interface CardReaderApi { suspend fun readWaiterCard(): CardReadResult }
sealed interface CardReadResult { data class Success(val panSha256: String): CardReadResult; data object Cancelled: CardReadResult; data class Error(val code: String, val message: String): CardReadResult }
interface PaymentApi { suspend fun pay(request: PaymentRequest): PaymentResult }
data class PaymentRequest(val totalAmountMinor: Long, val billAmountMinor: Long, val tipAmountMinor: Long, val currency: CurrencyCode, val waiterId: Long, val waiterFirstName: String, val waiterLastName: String, val waiterCardBindingHash: String, val terminalId: String, val extraJson: String)
sealed interface PaymentResult { data class Success(val externalTransactionId: String?, val approvedAmountMinor: Long): PaymentResult; data object Cancelled: PaymentResult; data class Error(val code: String, val message: String): PaymentResult }
