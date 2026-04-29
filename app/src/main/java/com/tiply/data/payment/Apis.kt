package com.tiply.data.payment

import com.tiply.domain.result.*
import kotlinx.coroutines.delay

class MockCardReaderApi: CardReaderApi { override suspend fun readWaiterCard(): CardReadResult { delay(200); return CardReadResult.Success("mock_pan_sha256") } }
class MockPaymentApi(private val forceError: Boolean = false): PaymentApi { override suspend fun pay(request: PaymentRequest): PaymentResult { delay(300); return if (forceError) PaymentResult.Error("MOCK_ERR", "Mock failure") else PaymentResult.Success("MOCK-${System.currentTimeMillis()}", request.totalAmountMinor) } }
class IntentPaymentApi: PaymentApi { override suspend fun pay(request: PaymentRequest): PaymentResult = PaymentResult.Error("TODO", "TODO: proprietary intent") }
class IntentCardReaderApi: CardReaderApi { override suspend fun readWaiterCard(): CardReadResult = CardReadResult.Error("TODO", "TODO: proprietary intent") }
