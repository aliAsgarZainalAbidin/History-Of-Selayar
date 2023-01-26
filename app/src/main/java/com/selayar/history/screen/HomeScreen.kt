package com.selayar.history.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.history.R
import com.selayar.history.component.TextSelayar
import com.selayar.history.main.Screen
import com.selayar.history.main.component.HistoryItem
import com.selayar.history.ui.theme.Typography
import com.selayar.history.viewmodel.HistoryViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalPagerApi::class
)
@Composable
fun HomeScreen(
    navController: NavController,
    historyViewModel: HistoryViewModel
) {
    val dataWisata by remember { historyViewModel.getAllWisata() }
    Log.d("TAG", "HomeScreen: ${dataWisata.size}")
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.SCANNERSCREEN.name)
                },
                backgroundColor = Color(red = 254, green = 216, blue = 25)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_qr_code_scanner_24),
                    contentDescription = "Scan Qr Code"
                )
            }
        }, floatingActionButtonPosition = FabPosition.Center
    ) { paddingvalues ->
        val lazyListState = rememberLazyListState()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (tvTitle, tvSubtitle, icSearch, lazyRow) = createRefs()

            TextSelayar(
                text = "Explore Selayar",
                modifier = Modifier
                    .constrainAs(tvTitle) {
                        top.linkTo(parent.top, 24.dp)
                        start.linkTo(parent.start, 24.dp)
                        end.linkTo(icSearch.start, 4.dp)
                        width = Dimension.fillToConstraints
                    },
                style = Typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            TextSelayar(
                text = "Wisata Sejarah Kepulauan Selayar",
                modifier = Modifier
                    .constrainAs(tvSubtitle) {
                        top.linkTo(tvTitle.bottom, 4.dp)
                        start.linkTo(tvTitle.start)
                        end.linkTo(icSearch.start, 4.dp)
                        width = Dimension.fillToConstraints
                    },
                style = Typography.body1
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "Search",
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.SEARCHSCREEN.name)
                    }
                    .constrainAs(icSearch) {
                        top.linkTo(tvTitle.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(tvSubtitle.bottom)
                        height = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 24.dp)
            )
            LazyRow(
                modifier = Modifier
                    .constrainAs(lazyRow) {
                        top.linkTo(tvSubtitle.bottom, 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                state = lazyListState
            ) {
                itemsIndexed(dataWisata) { index, value ->
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
}

fun LazyListState.animateScrollAndCentralizeItem(index: Int, scope: CoroutineScope) {
    val itemInfo = this.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    scope.launch {
        if (itemInfo != null) {
            val center = this@animateScrollAndCentralizeItem.layoutInfo.viewportEndOffset / 2
            val childCenter = itemInfo.offset + itemInfo.size / 2
            this@animateScrollAndCentralizeItem.animateScrollBy((childCenter - center).toFloat())
        } else {
            this@animateScrollAndCentralizeItem.animateScrollToItem(index)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen(rememberNavController(), hiltViewModel())
    }
}