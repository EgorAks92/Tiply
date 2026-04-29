package com.tiply.domain.usecase.payment

import com.tiply.domain.payment.PaymentApiRouter
import com.tiply.domain.payment.PaymentExtraJsonBuilder
import com.tiply.domain.model.*
import com.tiply.domain.repository.TransactionRepository
import com.tiply.domain.result.PaymentRequest
import com.tiply.domain.result.PaymentResult
import kotlinx.coroutines.flow.Flow

class StartPaymentUseCase(private val paymentRouter: PaymentApiRouter, private val txRepo: TransactionRepository, private val jsonBuilder: PaymentExtraJsonBuilder) {
 suspend operator fun invoke(waiter: Waiter, cardHash: String?, bill: Long, tip: Long, settings: AppSettings): Result<Long> {
  if (cardHash.isNullOrBlank()) return Result.failure(IllegalArgumentException("card required"))
  if (settings.selectedPaymentIntegrationMode == PaymentIntegrationMode.AAR && settings.terminalId.isBlank()) return Result.failure(IllegalArgumentException("terminal required"))
  val total = bill + tip
  val req = PaymentRequest(total,bill,tip,settings.selectedCurrency,waiter.id,waiter.firstName,waiter.lastName,cardHash,settings.terminalId,jsonBuilder.build(waiter.id,waiter.firstName,waiter.lastName,bill,tip,total,settings.selectedCurrency.name,settings.terminalId,"1.0",System.currentTimeMillis()))
  return when(val r = paymentRouter.route(settings.selectedPaymentIntegrationMode).pay(req)){ is PaymentResult.Success -> Result.success(txRepo.save(Transaction(waiterId=waiter.id,terminalId=settings.terminalId,externalTransactionId=r.externalTransactionId,billAmountMinor=bill,tipAmountMinor=tip,totalAmountMinor=total,currency=settings.selectedCurrency,status=TransactionStatus.SUCCESS,paymentMethod=settings.selectedPaymentIntegrationMode,paymentErrorCode=null,paymentErrorMessage=null,createdAt=System.currentTimeMillis()))); is PaymentResult.Cancelled -> Result.failure(IllegalStateException("cancelled")); is PaymentResult.Error -> Result.failure(IllegalStateException(r.message))}
 }
}
class GetTransactionUseCase(private val repo: TransactionRepository){ suspend operator fun invoke(id:Long)=repo.get(id) }
class ObserveWaiterTransactionsUseCase(private val repo: TransactionRepository){ operator fun invoke(waiterId:Long): Flow<List<Transaction>> = repo.observeByWaiter(waiterId)}
class GetWaiterTransactionSummaryUseCase { operator fun invoke(list: List<Transaction>): Pair<Int, Long> = list.size to list.sumOf { it.totalAmountMinor } }
