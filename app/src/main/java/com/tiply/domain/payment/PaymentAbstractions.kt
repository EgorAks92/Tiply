package com.tiply.domain.payment

import com.tiply.domain.model.PaymentIntegrationMode

interface PaymentApiRouter {
    fun route(mode: PaymentIntegrationMode): PaymentApi
}

interface CardReaderApiRouter {
    fun route(mode: PaymentIntegrationMode): CardReaderApi
}

interface PaymentExtraJsonBuilder {
    fun build(
        waiterId: Long,
        tipAmountMinor: Long,
        serviceFeeMinor: Long,
        feesCovered: Boolean
    ): String
}
