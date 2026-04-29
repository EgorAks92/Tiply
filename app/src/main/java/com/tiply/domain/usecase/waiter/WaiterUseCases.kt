package com.tiply.domain.usecase.waiter

import com.tiply.domain.model.Waiter
import com.tiply.domain.repository.WaiterRepository
import kotlinx.coroutines.flow.Flow

class CreateWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(first:String,last:String)=repo.save(Waiter(0,first,last,false,null,System.currentTimeMillis(),System.currentTimeMillis())) }
class ObserveWaitersUseCase(private val repo: WaiterRepository){ operator fun invoke(): Flow<List<Waiter>> = repo.observeWaiters() }
class GetWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(id:Long)=repo.getWaiter(id) }
class DeleteWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(id:Long)=repo.delete(id) }
class BindCardToWaiterUseCase(private val repo: WaiterRepository){ suspend operator fun invoke(waiterId:Long, hash:String)=repo.bindCard(waiterId, hash) }
class VerifyWaiterPinUseCase{ operator fun invoke(input:String,expected:String)=input==expected }
