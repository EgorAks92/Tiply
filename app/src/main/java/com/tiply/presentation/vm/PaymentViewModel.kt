package com.tiply.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiply.domain.model.*
import com.tiply.domain.usecase.MoneyParseResult
import com.tiply.domain.usecase.MoneyParser
import com.tiply.domain.usecase.payment.StartPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PaymentViewModel @Inject constructor(private val startPaymentUseCase: StartPaymentUseCase): ViewModel() {
 val amount=MutableStateFlow(""); val tipPercent=MutableStateFlow(0); val customTip=MutableStateFlow(""); val total=MutableStateFlow(0L); val txId=MutableStateFlow<Long?>(null); val error=MutableStateFlow<String?>(null)
 val waiterId=MutableStateFlow<Long?>(null); val currency=MutableStateFlow(CurrencyCode.RUB)
 fun loadContext(waiterId: Long, settings: AppSettings){ this.waiterId.value=waiterId; currency.value=settings.selectedCurrency }
 fun recalc(){ val p=MoneyParser().parse(amount.value, currency.value); val bill=(p as? MoneyParseResult.Success)?.amountMinor?:0; val tip= if(customTip.value.isNotBlank()) (MoneyParser().parse(customTip.value,currency.value,allowZero=true) as? MoneyParseResult.Success)?.amountMinor?:0 else bill*tipPercent.value/100; total.value=bill+tip }
 fun start(waiter: Waiter, card:String, settings: AppSettings){viewModelScope.launch{currency.value=settings.selectedCurrency; recalc(); val bill=(MoneyParser().parse(amount.value,currency.value) as? MoneyParseResult.Success)?.amountMinor?:0; val tip=(total.value-bill).coerceAtLeast(0); val r=startPaymentUseCase(waiter,card,bill,tip,settings); txId.value=r.getOrNull(); error.value=r.exceptionOrNull()?.message }}
}
