package com.tiply.domain.payment

import com.tiply.domain.model.PaymentIntegrationMode
import com.tiply.domain.payment.CardReaderApi
import com.tiply.domain.payment.PaymentApi

interface PaymentApiRouter { fun route(mode: PaymentIntegrationMode): PaymentApi }
interface CardReaderApiRouter { fun route(mode: PaymentIntegrationMode): CardReaderApi }
interface PaymentExtraJsonBuilder { fun build(waiterId: Long, waiterFirstName: String, waiterLastName: String, bill: Long, tip: Long, total: Long, currency: String, terminalId: String, appVersion: String, timestamp: Long): String }
