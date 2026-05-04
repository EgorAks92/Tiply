package com.tiply.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val cardShape = RoundedCornerShape(20.dp)
private val borderColor = Color.White.copy(alpha = 0.18f)

@Composable fun GlassScaffold(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF121629), Color(0xFF0B0E1A)))),
        content = content
    )
}

@Composable fun GlassCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = modifier.fillMaxWidth().border(1.dp, borderColor, cardShape), colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.08f)), shape = cardShape) {
        Column(Modifier.padding(16.dp), content = content)
    }
}

@Composable fun PrimaryGlassButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) = Button(onClick = onClick, modifier = modifier.fillMaxWidth().heightIn(min = 52.dp)) { Text(text) }
@Composable fun SecondaryGlassButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) = OutlinedButton(onClick = onClick, modifier = modifier.fillMaxWidth().heightIn(min = 52.dp)) { Text(text) }
@Composable fun DangerGlassButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) = Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3261E)), modifier = modifier.fillMaxWidth().heightIn(min = 52.dp)) { Text(text) }
@Composable fun AmountInputField(value: String, onValueChange: (String) -> Unit, label: String) = OutlinedTextField(value = value, onValueChange = onValueChange, label = { Text(label) }, modifier = Modifier.fillMaxWidth())
@Composable fun TipSelector(options: List<String>, selected: String, onSelect: (String) -> Unit) { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { options.forEach { FilterChip(selected = it == selected, onClick = { onSelect(it) }, label = { Text(it) }) } } }
@Composable fun WaiterCard(title: String, subtitle: String, actionText: String, onClick: () -> Unit) = GlassCard { Text(title, style = MaterialTheme.typography.titleMedium); Text(subtitle, style = MaterialTheme.typography.bodyMedium); Spacer(Modifier.height(8.dp)); SecondaryGlassButton(actionText, onClick) }
@Composable fun PaymentSummaryCard(billLabel: String, tipLabel: String, totalLabel: String, bill: String, tip: String, total: String) = GlassCard { Text("$billLabel: $bill"); Text("$tipLabel: $tip"); Text("$totalLabel: $total", style = MaterialTheme.typography.titleMedium) }
@Composable fun TransactionCard(line1: String, line2: String) = GlassCard { Text(line1); Text(line2) }
@Composable fun ErrorBanner(message: String) { Surface(color = Color(0x66B3261E), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) { Text(message, modifier = Modifier.padding(12.dp)) } }
@Composable fun LoadingOverlay(visible: Boolean) { if (visible) Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)), contentAlignment = Alignment.Center) { CircularProgressIndicator() } }
@Composable fun EmptyState(text: String) { Box(Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) { Text(text) } }

@Composable fun GlassList(content: @Composable ColumnScope.() -> Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { item { Column(content = content) } }
}
