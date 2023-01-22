package com.example.historyofselayar.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.historyofselayar.screen.DetailScreen
import com.example.historyofselayar.screen.HomeScreen
import com.example.historyofselayar.screen.ScannerScreen
import com.example.historyofselayar.viewmodel.HistoryViewModel

enum class Screen {
    HOMESCREEN, DETAILSCREEN, SCANNERSCREEN
}

@Composable
fun MainNav(historyViewModel: HistoryViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HOMESCREEN.name) {
        composable(Screen.HOMESCREEN.name) {
            HomeScreen(navController,historyViewModel)
        }
        composable(Screen.DETAILSCREEN.name) {
            DetailScreen(
                urlImage = "https://images.pexels.com/photos/15286/pexels-photo.jpg?cs=srgb&dl=pexels-luis-del-r%C3%ADo-15286.jpg&fm=jpg",
                navController,
                historyViewModel
            )
        }
        composable(Screen.SCANNERSCREEN.name){
            ScannerScreen(
                navController,
                historyViewModel
            )
        }
    }
}