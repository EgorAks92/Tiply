package com.tiply.domain.usecase.waiter

import com.tiply.domain.model.Waiter
import com.tiply.domain.payment.CardReadResult
import com.tiply.domain.payment.CardReaderApiRouter
import com.tiply.domain.repository.SettingsRepository
import com.tiply.domain.repository.WaiterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CreateWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(first:String,last:String,pin:String)=repo.create(first,last,pin) }
class ObserveWaitersUseCase(private val repo: WaiterRepository){ operator fun invoke(): Flow<List<Waiter>> = repo.observeWaiters() }
class GetWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(id:Long)=repo.getWaiter(id) }
class DeleteWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(id:Long)=repo.delete(id) }
class BindCardToWaiterUseCase(private val repo: WaiterRepository, private val settingsRepository: SettingsRepository, private val readerRouter: CardReaderApiRouter){ suspend operator fun invoke(waiterId:Long): Result<Unit> { val mode=settingsRepository.observe().first().selectedPaymentIntegrationMode; return when(val r=readerRouter.route(mode).readWaiterCard()){ is CardReadResult.Success -> { repo.bindCard(waiterId,r.panSha256); Result.success(Unit)}; is CardReadResult.Cancelled -> Result.failure(IllegalStateException("cancelled")); is CardReadResult.Error -> Result.failure(IllegalStateException(r.message)) } } }
class VerifyWaiterPinUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(waiterId:Long,pin:String)=repo.verifyPin(waiterId,pin) }
