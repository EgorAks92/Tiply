package com.tiply.presentation.vm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.model.Waiter
import com.tiply.domain.usecase.waiter.DeleteWaiterUseCase
import com.tiply.domain.usecase.waiter.GetWaiterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
@HiltViewModel class WaiterProfileViewModel @Inject constructor(private val get: GetWaiterUseCase, private val del: DeleteWaiterUseCase): ViewModel(){ val waiter=MutableStateFlow<Waiter?>(null); fun load(id:Long){viewModelScope.launch{waiter.value=get(id)}}; fun delete(){ waiter.value?.let{viewModelScope.launch{del(it.id)}} }; val hasBoundCard get() = waiter.value?.hasBoundCard==true }
