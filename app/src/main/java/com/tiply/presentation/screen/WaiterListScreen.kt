package com.tiply.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tiply.R
import com.tiply.presentation.components.GlassCard
import com.tiply.presentation.components.GlassScaffold
import com.tiply.presentation.components.PrimaryGlassButton
import com.tiply.presentation.vm.WaiterListViewModel

@Composable
fun WaiterListScreen(nav: NavController, vm: WaiterListViewModel = hiltViewModel()) {
 val waiters by vm.waiters.collectAsStateWithLifecycle()
 GlassScaffold { Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
  PrimaryGlassButton(stringResource(R.string.create_waiter)) { nav.navigate("waiter_create") }
  PrimaryGlassButton(stringResource(R.string.settings)) { nav.navigate("settings") }
  LazyColumn { items(waiters){ w -> GlassCard { PrimaryGlassButton("${w.firstName} ${w.lastName}") { nav.navigate("waiter_profile/${w.id}") } } } }
 } }
}
