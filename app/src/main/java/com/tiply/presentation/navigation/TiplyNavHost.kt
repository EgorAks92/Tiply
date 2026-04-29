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
  composable("waiter_list") { WaiterListScreen() }
  composable("waiter_create") { WaiterCreateScreen() }
  composable("waiter_profile/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { WaiterProfileScreen() }
  composable("bind_card/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { BindCardScreen() }
  composable("payment/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { PaymentScreen() }
  composable("payment_result/{transactionId}", arguments=listOf(navArgument("transactionId"){type=NavType.LongType})) { PaymentResultScreen() }
  composable("history_pin/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { HistoryPinScreen() }
  composable("waiter_history/{waiterId}", arguments=listOf(navArgument("waiterId"){type=NavType.LongType})) { WaiterHistoryScreen() }
  composable("settings") { SettingsScreen() }
 }
}
