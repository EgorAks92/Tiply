package com.tiply.presentation.vm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.usecase.waiter.VerifyWaiterPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
@HiltViewModel class HistoryPinViewModel @Inject constructor(private val verify: VerifyWaiterPinUseCase): ViewModel(){ val pin=MutableStateFlow(""); val ok=MutableStateFlow(false); val error=MutableStateFlow<String?>(null); fun verifyPin(waiterId:Long){viewModelScope.launch{ok.value=verify(waiterId,pin.value); error.value=if(ok.value) null else "wrong"}} }
