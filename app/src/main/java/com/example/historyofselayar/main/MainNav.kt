package com.example.historyofselayar.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.historyofselayar.screen.DetailScreen
import com.example.historyofselayar.screen.HomeScreen
import com.example.historyofselayar.screen.ScannerScreen
import com.example.historyofselayar.screen.SearchScreen
import com.example.historyofselayar.viewmodel.HistoryViewModel

enum class Screen {
    HOMESCREEN, DETAILSCREEN, SCANNERSCREEN, SEARCHSCREEN
}

@Composable
fun MainNav(historyViewModel: HistoryViewModel) {
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current
    NavHost(navController = navController, startDestination = Screen.HOMESCREEN.name) {
        composable(Screen.HOMESCREEN.name) {
            HomeScreen(navController,historyViewModel)
        }
        composable("${Screen.DETAILSCREEN.name}/{id}", arguments = arrayListOf(
            navArgument("id"){
                type = NavType.StringType
                defaultValue = "-1"
            }
        )) {
            DetailScreen(
                id = it.arguments?.getString("id")?.toInt() ?: "-1".toInt(),
                navController,
                historyViewModel
            )
        }
        composable(Screen.SCANNERSCREEN.name){
            ScannerScreen(
                navController,
                historyViewModel,
                lifecycleOwner
            )
        }
        composable(Screen.SEARCHSCREEN.name){
            SearchScreen(navController = navController, historyViewModel = historyViewModel)
        }
    }
}