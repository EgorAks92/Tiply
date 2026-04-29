package com.tiply.domain.model

enum class CurrencyCode { RUB, AMD }
enum class TransactionStatus { SUCCESS, CANCELLED, ERROR }
enum class PaymentIntegrationMode { MOCK, INTENT, AAR }
enum class AppLanguage { RU, HY }

data class Waiter(val id: Long, val firstName: String, val lastName: String, val hasBoundCard: Boolean, val cardBindingCreatedAt: Long?, val createdAt: Long, val updatedAt: Long)
data class Transaction(val id: Long = 0, val waiterId: Long, val terminalId: String, val externalTransactionId: String?, val billAmountMinor: Long, val tipAmountMinor: Long, val totalAmountMinor: Long, val currency: CurrencyCode, val status: TransactionStatus, val paymentMethod: PaymentIntegrationMode, val paymentErrorCode: String?, val paymentErrorMessage: String?, val createdAt: Long)
data class AppSettings(val selectedLanguage: AppLanguage = AppLanguage.RU, val selectedCurrency: CurrencyCode = CurrencyCode.RUB, val selectedPaymentIntegrationMode: PaymentIntegrationMode = PaymentIntegrationMode.MOCK, val terminalId: String = "")
