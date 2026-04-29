package com.tiply.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tiply.R
import com.tiply.presentation.components.*

@Composable
fun WaiterProfileScreen() {
 GlassScaffold {
  Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
   GlassCard { Text(stringResource(R.string.app_name)); Text(stringResource(R.string.summary)) }
   EmptyState(stringResource(R.string.payment_in_progress))
   PrimaryGlassButton(text = stringResource(R.string.retry), onClick = {})
  }
 }
}
