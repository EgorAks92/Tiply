package com.tiply.presentation.screen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tiply.presentation.components.*
import com.tiply.presentation.vm.WaiterHistoryViewModel
@Composable fun WaiterHistoryScreen(nav: NavController, waiterId: Long, vm: WaiterHistoryViewModel = hiltViewModel()){ val itemsList by vm.list.collectAsStateWithLifecycle(); LaunchedEffect(waiterId){vm.load(waiterId)}; GlassScaffold{ LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){ items(itemsList){ t -> TransactionCard("${t.status}", "${t.totalAmountMinor}") } } } }
