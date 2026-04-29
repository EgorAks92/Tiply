package com.tiply.data.payment

import com.tiply.domain.model.PaymentIntegrationMode
import com.tiply.domain.payment.*
import com.tiply.domain.result.*
import org.json.JSONObject

class AarPaymentSdkFacade: PaymentSdkFacade { override suspend fun pay(params: SdkTransactionParams)=SdkTransactionResult(false,errorCode="TODO",errorMessage="TODO SDK"); override suspend fun readCard()=SdkTransactionResult(false,errorCode="TODO",errorMessage="TODO SDK") }
class AarPaymentApi(private val sdk: PaymentSdkFacade): PaymentApi { override suspend fun pay(request: PaymentRequest): PaymentResult { val r=sdk.pay(SdkTransactionParams(request.totalAmountMinor,request.currency.name,request.terminalId,request.extraJson)); return if(r.success) PaymentResult.Success(r.externalId, request.totalAmountMinor) else PaymentResult.Error(r.errorCode?:"AAR", r.errorMessage?:"error") } }
class AarCardReaderApi(private val sdk: PaymentSdkFacade): CardReaderApi { override suspend fun readWaiterCard(): CardReadResult { val r=sdk.readCard(); return if(r.success) CardReadResult.Success(r.externalId?:"") else CardReadResult.Error(r.errorCode?:"AAR", r.errorMessage?:"error") } }
class PaymentRouter(private val mock: PaymentApi, private val intent: PaymentApi, private val aar: PaymentApi){ fun route(mode: PaymentIntegrationMode)= when(mode){PaymentIntegrationMode.MOCK->mock; PaymentIntegrationMode.INTENT->intent; PaymentIntegrationMode.AAR->aar}}
class CardReaderRouter(private val mock: CardReaderApi, private val intent: CardReaderApi, private val aar: CardReaderApi){ fun route(mode: PaymentIntegrationMode)= when(mode){PaymentIntegrationMode.MOCK->mock; PaymentIntegrationMode.INTENT->intent; PaymentIntegrationMode.AAR->aar}}

class DefaultPaymentExtraJsonBuilder { fun build(waiterId: Long, waiterFirstName: String, waiterLastName: String, bill: Long, tip: Long, total: Long, currency: String, terminalId: String, appVersion: String, timestamp: Long): String = JSONObject(mapOf("waiterId" to waiterId,"waiterFirstName" to waiterFirstName,"waiterLastName" to waiterLastName,"billAmountMinor" to bill,"tipAmountMinor" to tip,"totalAmountMinor" to total,"currency" to currency,"terminalId" to terminalId,"appVersion" to appVersion,"timestamp" to timestamp)).toString() }
