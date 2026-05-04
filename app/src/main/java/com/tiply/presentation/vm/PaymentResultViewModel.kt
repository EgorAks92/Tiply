package com.tiply.presentation.vm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.model.Transaction
import com.tiply.domain.usecase.payment.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
@HiltViewModel class PaymentResultViewModel @Inject constructor(private val get: GetTransactionUseCase): ViewModel(){ val tx=MutableStateFlow<Transaction?>(null); fun load(id:Long){viewModelScope.launch{tx.value=get(id)}} }
