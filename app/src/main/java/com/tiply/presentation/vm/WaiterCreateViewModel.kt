package com.tiply.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.usecase.waiter.CreateWaiterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel class WaiterCreateViewModel @Inject constructor(private val create: CreateWaiterUseCase): ViewModel(){
 val firstName=MutableStateFlow(""); val lastName=MutableStateFlow(""); val pin=MutableStateFlow(""); val repeatPin=MutableStateFlow("")
 private val _error=MutableStateFlow<String?>(null); val error=_error.asStateFlow(); val success=MutableStateFlow(false)
 fun createWaiter(){ viewModelScope.launch { if(firstName.value.isBlank()||lastName.value.isBlank()||pin.value.length<4||pin.value!=repeatPin.value){_error.value="validation";return@launch}; create(firstName.value,lastName.value,pin.value); success.value=true } }
}
