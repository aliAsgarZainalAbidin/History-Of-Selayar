package com.selayar.history.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.history.R
import com.selayar.history.main.Screen
import com.selayar.history.main.component.HistoryItem
import com.selayar.history.viewmodel.HistoryViewModel

@OptIn(ExperimentalMaterialApi::class)
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
        OutlinedTextField(
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
                    top.linkTo(textField.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(listItem.value) { index, value ->
                Card(
                    onClick = {
                        navController.navigate("${Screen.DETAILSCREEN.name}/${value.id}")
                        Log.d("TAG", "HomeScreen: ${Screen.DETAILSCREEN.name}/${value.id}")
                    },
                    modifier = Modifier.padding(horizontal = 8.dp),
                    elevation =4.dp
                ) {
                    HistoryItem(value, onItemClickListener = {})
                }
            }
        }
    }
}