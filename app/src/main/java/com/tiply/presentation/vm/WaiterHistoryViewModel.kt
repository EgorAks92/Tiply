package com.tiply.presentation.vm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.model.Transaction
import com.tiply.domain.usecase.payment.ObserveWaiterTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
@HiltViewModel class WaiterHistoryViewModel @Inject constructor(private val observe: ObserveWaiterTransactionsUseCase): ViewModel(){ val list=MutableStateFlow<List<Transaction>>(emptyList()); fun load(waiterId:Long){viewModelScope.launch{observe(waiterId).collect{list.value=it}}} }
