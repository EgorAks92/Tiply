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
import com.tiply.presentation.vm.HistoryPinViewModel
@Composable fun HistoryPinScreen(nav: NavController, waiterId: Long, vm: HistoryPinViewModel = hiltViewModel()){ val pin by vm.pin.collectAsStateWithLifecycle(); val ok by vm.ok.collectAsStateWithLifecycle(); val err by vm.error.collectAsStateWithLifecycle(); if(ok) LaunchedEffect(ok){nav.navigate("waiter_history/$waiterId")}; GlassScaffold{ Column(Modifier.fillMaxSize().padding(16.dp),verticalArrangement=Arrangement.spacedBy(8.dp)){ AmountInputField(pin,{vm.pin.value=it},stringResource(R.string.pin)); PrimaryGlassButton(stringResource(R.string.open_history)){vm.verifyPin(waiterId)}; err?.let{ErrorBanner(it)} } } }
