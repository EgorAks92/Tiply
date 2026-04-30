package com.tiply.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tiply.presentation.screen.*

@Composable
fun TiplyNavHost() {
 val nav = rememberNavController()
 NavHost(navController = nav, startDestination = "waiter_list") {
  composable("waiter_list") { WaiterListScreen(nav) }
  composable("waiter_create") { WaiterCreateScreen(nav) }
  composable("waiter_profile/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { WaiterProfileScreen(nav, it.arguments?.getLong("waiterId") ?: 0L) }
  composable("bind_card/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { BindCardScreen(nav, it.arguments?.getLong("waiterId") ?: 0L) }
  composable("payment/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { PaymentScreen(nav, it.arguments?.getLong("waiterId") ?: 0L) }
  composable("payment_result/{transactionId}", arguments=listOf(navArgument("transactionId"){type=NavType.LongType})) { PaymentResultScreen(nav, it.arguments?.getLong("transactionId") ?: 0L) }
  composable("history_pin/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { HistoryPinScreen(nav, it.arguments?.getLong("waiterId") ?: 0L) }
  composable("waiter_history/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { WaiterHistoryScreen(nav, it.arguments?.getLong("waiterId") ?: 0L) }
  composable("settings") { SettingsScreen(nav) }
 }
}
