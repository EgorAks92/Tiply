package com.tiply.presentation.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentResultViewModel: ViewModel() { private val _state = MutableStateFlow(""); val state: StateFlow<String> = _state }
