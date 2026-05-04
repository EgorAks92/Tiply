package com.tiply.presentation.screen
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tiply.presentation.components.*
import com.tiply.presentation.vm.PaymentResultViewModel
@Composable fun PaymentResultScreen(nav: NavController, transactionId: Long, vm: PaymentResultViewModel = hiltViewModel()){ val tx by vm.tx.collectAsStateWithLifecycle(); LaunchedEffect(transactionId){vm.load(transactionId)}; GlassScaffold{ Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement=Arrangement.spacedBy(8.dp)){ GlassCard{ androidx.compose.material3.Text("${tx?.status}"); androidx.compose.material3.Text("${tx?.totalAmountMinor}"); androidx.compose.material3.Text("${tx?.paymentErrorMessage ?: ""}") } } } }
