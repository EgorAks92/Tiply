package com.tiply.domain.usecase.waiter

import com.tiply.domain.model.Waiter
import com.tiply.domain.repository.WaiterRepository
import kotlinx.coroutines.flow.Flow

class CreateWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(first:String,last:String,pin:String)=repo.create(first,last,pin) }
class ObserveWaitersUseCase(private val repo: WaiterRepository){ operator fun invoke(): Flow<List<Waiter>> = repo.observeWaiters() }
class GetWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(id:Long)=repo.getWaiter(id) }
class DeleteWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(id:Long)=repo.delete(id) }
class BindCardToWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(waiterId:Long, hash:String)=repo.bindCard(waiterId, hash) }
class VerifyWaiterPinUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(waiterId:Long,pin:String)=repo.verifyPin(waiterId,pin) }
