package com.example.historyofselayar.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.historyofselayar.screen.HomeScreen
import com.example.historyofselayar.screen.ScannerScreen
import com.example.historyofselayar.screen.SearchScreen
import com.example.historyofselayar.ui.theme.HistoryOfSelayarTheme
import com.example.historyofselayar.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val historyViewModel by viewModels<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HistoryOfSelayarTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {_ ->
                    MainNav(historyViewModel)
                }
            }
        }
    }
}
