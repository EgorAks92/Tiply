package com.tiply.presentation.vm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.usecase.waiter.BindCardToWaiterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
@HiltViewModel class BindCardViewModel @Inject constructor(private val bind: BindCardToWaiterUseCase): ViewModel(){ val loading=MutableStateFlow(false); val success=MutableStateFlow(false); val error=MutableStateFlow<String?>(null); fun readAndBind(waiterId:Long){viewModelScope.launch{loading.value=true; val r=bind(waiterId); loading.value=false; success.value=r.isSuccess; error.value=r.exceptionOrNull()?.message}} }
