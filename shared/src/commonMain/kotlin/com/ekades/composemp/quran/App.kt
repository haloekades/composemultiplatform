package com.ekades.composemp.quran

import QuranListScreen
import SurahDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ekades.composemp.quran.data.QuranRepository
import com.ekades.composemp.quran.presentation.quran.QuranViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun App() {
    val viewModel = remember {
        QuranViewModel(QuranRepository(), CoroutineScope(Dispatchers.Main))
    }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            QuranListScreen(viewModel = viewModel, onSurahClick = { nomor ->
                navController.navigate("detail/$nomor")
            })
        }
        composable("detail/{nomor}") { backStackEntry ->
            val nomor = backStackEntry.arguments?.getString("nomor")?.toIntOrNull()
            nomor?.let {
                SurahDetailScreen(navController = navController, viewModel= viewModel, number = it)
            }
        }
    }
}
