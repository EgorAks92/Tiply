package com.tiply.data.payment

import com.tiply.domain.result.*
import kotlinx.coroutines.delay

class MockCardReaderApi: CardReaderApi { override suspend fun readWaiterCard(): CardReadResult { delay(500); return CardReadResult.Success("mock_pan_sha256_8f2a3") } }
class MockPaymentApi(private val forceError: Boolean = false): PaymentApi { override suspend fun pay(request: PaymentRequest): PaymentResult { delay(700); return if (forceError) PaymentResult.Error("MOCK_ERR", "Mock failure") else PaymentResult.Success("MOCK-${System.currentTimeMillis()}", request.totalAmountMinor) } }
class IntentPaymentApi: PaymentApi { override suspend fun pay(request: PaymentRequest): PaymentResult = PaymentResult.Error("TODO", "TODO: proprietary intent") }
class IntentCardReaderApi: CardReaderApi { override suspend fun readWaiterCard(): CardReadResult = CardReadResult.Error("TODO", "TODO: proprietary intent") }

interface PaymentSdkFacade
class AarPaymentSdkFacade: PaymentSdkFacade { /* TODO import real SDK classes only here. */ }
class AarPaymentApi: PaymentApi { override suspend fun pay(request: PaymentRequest): PaymentResult = PaymentResult.Error("TODO", "TODO AAR mapping") }
class AarCardReaderApi: CardReaderApi { override suspend fun readWaiterCard(): CardReadResult = CardReadResult.Error("TODO", "TODO AAR mapping") }
