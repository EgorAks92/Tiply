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
import com.tiply.presentation.vm.BindCardViewModel
@Composable fun BindCardScreen(nav: NavController, waiterId: Long, vm: BindCardViewModel = hiltViewModel()){ val loading by vm.loading.collectAsStateWithLifecycle(); val success by vm.success.collectAsStateWithLifecycle(); val error by vm.error.collectAsStateWithLifecycle(); GlassScaffold{ Column(Modifier.fillMaxSize().padding(16.dp),verticalArrangement=Arrangement.spacedBy(8.dp)){ PrimaryGlassButton(stringResource(R.string.bind_card)){vm.readAndBind(waiterId)}; if(loading) LoadingOverlay(true); if(success) EmptyState(stringResource(R.string.card_bound_successfully)); error?.let{ErrorBanner(it)} } } }
