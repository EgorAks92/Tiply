package com.tiply.domain.payment

data class SdkTransactionParams(val totalAmountMinor: Long, val currency: String, val terminalId: String, val extraJson: String)
data class SdkTransactionResult(val success: Boolean, val externalId: String? = null, val errorCode: String? = null, val errorMessage: String? = null)
interface PaymentSdkFacade { suspend fun pay(params: SdkTransactionParams): SdkTransactionResult; suspend fun readCard(): SdkTransactionResult }
