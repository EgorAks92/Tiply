package com.tiply.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.model.Waiter
import com.tiply.domain.usecase.waiter.ObserveWaitersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel class WaiterListViewModel @Inject constructor(observe: ObserveWaitersUseCase): ViewModel() { val waiters: StateFlow<List<Waiter>> = observe().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()) }
