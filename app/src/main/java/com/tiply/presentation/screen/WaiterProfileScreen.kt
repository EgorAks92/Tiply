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
import com.tiply.presentation.vm.WaiterProfileViewModel
@Composable fun WaiterProfileScreen(nav: NavController, waiterId: Long, vm: WaiterProfileViewModel = hiltViewModel()){ val w by vm.waiter.collectAsStateWithLifecycle(); LaunchedEffect(waiterId){vm.load(waiterId)}; GlassScaffold{ Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){ GlassCard{ androidx.compose.material3.Text("${w?.firstName ?: ""} ${w?.lastName ?: ""}"); androidx.compose.material3.Text(if(vm.hasBoundCard) stringResource(R.string.card_bound) else stringResource(R.string.card_not_bound))}; PrimaryGlassButton(stringResource(R.string.bind_card)){nav.navigate("bind_card/$waiterId")}; PrimaryGlassButton(stringResource(R.string.payment)){nav.navigate("payment/$waiterId")}; PrimaryGlassButton(stringResource(R.string.history)){nav.navigate("history_pin/$waiterId")}; DangerGlassButton(stringResource(R.string.delete)){vm.delete(); nav.popBackStack()} } } }
