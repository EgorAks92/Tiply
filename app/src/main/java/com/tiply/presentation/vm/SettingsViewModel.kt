package com.tiply.presentation.vm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.model.*
import com.tiply.domain.usecase.settings.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
@HiltViewModel class SettingsViewModel @Inject constructor(private val observe: ObserveAppSettingsUseCase, private val uLang: UpdateLanguageUseCase, private val uCur: UpdateCurrencyUseCase, private val uMode: UpdatePaymentIntegrationModeUseCase, private val uTerm: UpdateTerminalIdUseCase): ViewModel(){ val settings=observe().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSettings()); fun updateLanguage(v:AppLanguage){viewModelScope.launch{uLang(v)}}; fun updateCurrency(v:CurrencyCode){viewModelScope.launch{uCur(v)}}; fun updateMode(v:PaymentIntegrationMode){viewModelScope.launch{uMode(v)}}; fun updateTerminal(v:String){viewModelScope.launch{uTerm(v)}} }
