package com.tiply.presentation.screen
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tiply.R
import com.tiply.presentation.components.*
import com.tiply.presentation.vm.PaymentViewModel
@Composable fun PaymentScreen(nav: NavController, waiterId: Long, vm: PaymentViewModel = hiltViewModel()){ val amount by vm.amount.collectAsStateWithLifecycle(); val total by vm.total.collectAsStateWithLifecycle(); val err by vm.error.collectAsStateWithLifecycle(); GlassScaffold{ Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){ AmountInputField(amount,{vm.amount.value=it; vm.recalc()},stringResource(R.string.bill_amount)); TipSelector(listOf("0%","5%","10%","15%"),"${vm.tipPercent.value}%"){vm.tipPercent.value=it.removeSuffix("%").toInt(); vm.recalc()}; AmountInputField(vm.customTip.value,{vm.customTip.value=it; vm.recalc()},stringResource(R.string.custom_tip)); PaymentSummaryCard(stringResource(R.string.bill_total), stringResource(R.string.tips_total), stringResource(R.string.total), amount, "", total.toString()); err?.let{ErrorBanner(it)} } } }
