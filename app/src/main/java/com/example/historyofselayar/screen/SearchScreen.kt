package com.example.historyofselayar.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.historyofselayar.R
import com.example.historyofselayar.component.TextSelayar
import com.example.historyofselayar.main.Screen
import com.example.historyofselayar.main.component.HistoryItem
import com.example.historyofselayar.ui.theme.Typography
import com.example.historyofselayar.viewmodel.HistoryViewModel

@Composable
fun SearchScreen(navController: NavController, historyViewModel: HistoryViewModel) {
    var tfSearchValue by remember { mutableStateOf("") }
    var listItem by remember { mutableStateOf(historyViewModel.searchWisata(tfSearchValue)) }
    val focusRequest by remember { mutableStateOf(FocusRequester()) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (textField, lazyRow) = createRefs()
        TextField(
            maxLines = 1,
            value = tfSearchValue,
            onValueChange = {
                tfSearchValue = it
                listItem = historyViewModel.searchWisata(tfSearchValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequest)
                .focusProperties {
                    focusRequest.requestFocus()
                }
                .wrapContentHeight()
                .constrainAs(textField) {
                    top.linkTo(parent.top, 32.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "Search",
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            },
            placeholder = {
                Text("Cari Wisata")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )

        LazyRow(
            modifier = Modifier
                .constrainAs(lazyRow) {
                    top.linkTo(textField.bottom, 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(listItem.value) { index, value ->
                HistoryItem(
                    value,
                    onItemClickListener = {
                        navController.navigate("${Screen.DETAILSCREEN.name}/${value.id}")
                    })
            }
        }
    }
}