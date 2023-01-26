package com.selayar.history.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.selayar.history.ui.theme.HistoryOfSelayarTheme
import com.selayar.history.viewmodel.HistoryViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val historyViewModel by viewModels<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            HistoryOfSelayarTheme {
                // A surface container using the 'background' color from the theme
                SideEffect {
                    systemUiController.setStatusBarColor(Color.Blue)
                }
                Scaffold {_ ->
                    MainNav(historyViewModel)
                }
            }
        }
    }
}
