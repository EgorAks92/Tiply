package com.tiply.usecase

import com.tiply.data.payment.DefaultPaymentExtraJsonBuilder
import com.tiply.data.payment.PaymentRouter
import com.tiply.domain.model.*
import com.tiply.domain.repository.TransactionRepository
import com.tiply.domain.payment.*
import com.tiply.domain.usecase.payment.StartPaymentUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class StartPaymentUseCaseTest {
 @Test fun validations() = runBlocking {
  val router = PaymentRouter(object: PaymentApi{override suspend fun pay(request: PaymentRequest)=PaymentResult.Success("x",request.totalAmountMinor)}, object: PaymentApi{override suspend fun pay(request: PaymentRequest)=PaymentResult.Success("x",request.totalAmountMinor)}, object: PaymentApi{override suspend fun pay(request: PaymentRequest)=PaymentResult.Success("x",request.totalAmountMinor)})
  val repo = object: TransactionRepository{ override suspend fun save(tx: Transaction)=1L; override suspend fun get(id: Long)=null; override fun observeByWaiter(waiterId: Long): Flow<List<Transaction>> = emptyFlow() }
  val uc = StartPaymentUseCase(router, repo, DefaultPaymentExtraJsonBuilder())
  val waiter = Waiter(1,"A","B",true,null,0,0)
  assertTrue(uc(waiter,null,100,10,AppSettings()).isFailure)
  assertTrue(uc(waiter,"hash",100,10,AppSettings(selectedPaymentIntegrationMode = PaymentIntegrationMode.AAR, terminalId = "")).isFailure)
 }
}
