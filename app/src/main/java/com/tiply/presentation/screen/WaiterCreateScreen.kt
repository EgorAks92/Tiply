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
import com.tiply.presentation.vm.WaiterCreateViewModel

@Composable fun WaiterCreateScreen(nav: NavController, vm: WaiterCreateViewModel = hiltViewModel()) {
 val first by vm.firstName.collectAsStateWithLifecycle(); val last by vm.lastName.collectAsStateWithLifecycle(); val pin by vm.pin.collectAsStateWithLifecycle(); val repeat by vm.repeatPin.collectAsStateWithLifecycle(); val err by vm.error.collectAsStateWithLifecycle(); val ok by vm.success.collectAsStateWithLifecycle()
 if(ok) LaunchedEffect(ok){ nav.popBackStack() }
 GlassScaffold { Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { AmountInputField(first,{vm.firstName.value=it},stringResource(R.string.first_name)); AmountInputField(last,{vm.lastName.value=it},stringResource(R.string.last_name)); AmountInputField(pin,{vm.pin.value=it},stringResource(R.string.pin)); AmountInputField(repeat,{vm.repeatPin.value=it},stringResource(R.string.repeat_pin)); err?.let{ ErrorBanner(it) }; PrimaryGlassButton(stringResource(R.string.save)){vm.createWaiter()} } }
}
