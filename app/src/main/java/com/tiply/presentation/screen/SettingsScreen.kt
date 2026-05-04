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
import com.tiply.presentation.vm.SettingsViewModel
@Composable fun SettingsScreen(nav: NavController, vm: SettingsViewModel = hiltViewModel()){ val s by vm.settings.collectAsStateWithLifecycle(); var terminal by remember { mutableStateOf("") }; GlassScaffold{ Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){ EmptyState("${s.selectedCurrency} / ${s.selectedLanguage} / ${s.selectedPaymentIntegrationMode}"); AmountInputField(terminal,{terminal=it},stringResource(R.string.terminal_id)); PrimaryGlassButton(stringResource(R.string.save)){vm.updateTerminal(terminal)} } } }
