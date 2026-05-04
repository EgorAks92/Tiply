package com.tiply.domain.usecase.payment

import com.tiply.domain.model.*
import com.tiply.domain.payment.PaymentApiRouter
import com.tiply.domain.payment.PaymentExtraJsonBuilder
import com.tiply.domain.payment.PaymentRequest
import com.tiply.domain.payment.PaymentResult
import com.tiply.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class StartPaymentUseCase(private val paymentRouter: PaymentApiRouter, private val txRepo: TransactionRepository, private val jsonBuilder: PaymentExtraJsonBuilder) {
 suspend operator fun invoke(waiter: Waiter, cardHash: String?, bill: Long, tip: Long, settings: AppSettings): Result<Long> {
  if (bill <= 0) return Result.failure(IllegalArgumentException("bill must be positive"))
  if (tip < 0) return Result.failure(IllegalArgumentException("tip must be non-negative"))
  if (cardHash.isNullOrBlank()) return Result.failure(IllegalArgumentException("card required"))
  if (settings.selectedPaymentIntegrationMode == PaymentIntegrationMode.AAR && settings.terminalId.isBlank()) return Result.failure(IllegalArgumentException("terminal required"))
  val total = bill + tip
  val req = PaymentRequest(total,bill,tip,settings.selectedCurrency,waiter.id,waiter.firstName,waiter.lastName,cardHash,settings.terminalId,jsonBuilder.build(waiter.id,waiter.firstName,waiter.lastName,bill,tip,total,settings.selectedCurrency.name,settings.terminalId,"1.0",System.currentTimeMillis()))
  val result = paymentRouter.route(settings.selectedPaymentIntegrationMode).pay(req)
  val tx = when(result){
   is PaymentResult.Success -> Transaction(waiterId=waiter.id,terminalId=settings.terminalId,externalTransactionId=result.externalTransactionId,billAmountMinor=bill,tipAmountMinor=tip,totalAmountMinor=total,currency=settings.selectedCurrency,status=TransactionStatus.SUCCESS,paymentMethod=settings.selectedPaymentIntegrationMode,paymentErrorCode=null,paymentErrorMessage=null,createdAt=System.currentTimeMillis())
   is PaymentResult.Cancelled -> Transaction(waiterId=waiter.id,terminalId=settings.terminalId,externalTransactionId=null,billAmountMinor=bill,tipAmountMinor=tip,totalAmountMinor=total,currency=settings.selectedCurrency,status=TransactionStatus.CANCELLED,paymentMethod=settings.selectedPaymentIntegrationMode,paymentErrorCode="CANCELLED",paymentErrorMessage="Cancelled",createdAt=System.currentTimeMillis())
   is PaymentResult.Error -> Transaction(waiterId=waiter.id,terminalId=settings.terminalId,externalTransactionId=null,billAmountMinor=bill,tipAmountMinor=tip,totalAmountMinor=total,currency=settings.selectedCurrency,status=TransactionStatus.ERROR,paymentMethod=settings.selectedPaymentIntegrationMode,paymentErrorCode=result.code,paymentErrorMessage=result.message,createdAt=System.currentTimeMillis())
  }
  return Result.success(txRepo.save(tx))
 }
}
class GetTransactionUseCase(private val repo: TransactionRepository){ suspend operator fun invoke(id:Long)=repo.get(id) }
class ObserveWaiterTransactionsUseCase(private val repo: TransactionRepository){ operator fun invoke(waiterId:Long): Flow<List<Transaction>> = repo.observeByWaiter(waiterId)}
class GetWaiterTransactionSummaryUseCase { operator fun invoke(list: List<Transaction>): Pair<Int, Long> = list.size to list.sumOf { it.totalAmountMinor } }
