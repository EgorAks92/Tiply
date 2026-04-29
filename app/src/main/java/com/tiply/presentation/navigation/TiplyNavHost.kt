package com.tiply.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TiplyNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "waiter_list") {
        listOf("waiter_list","waiter_create","waiter_profile/{waiterId}","bind_card/{waiterId}","payment/{waiterId}","payment_result/{transactionId}","history_pin/{waiterId}","waiter_history/{waiterId}","settings").forEach { r -> composable(r) { Text(r) } }
    }
}
