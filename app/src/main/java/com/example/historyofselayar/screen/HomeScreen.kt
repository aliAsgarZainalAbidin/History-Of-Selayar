package com.example.historyofselayar.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.historyofselayar.R
import com.example.historyofselayar.component.TextSelayar
import com.example.historyofselayar.main.component.HistoryItem
import com.example.historyofselayar.ui.theme.Typography

@Composable
fun HomeScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = Color(red = 254, green = 216, blue = 25)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_qr_code_scanner_24),
                    contentDescription = "Scan Qr Code"
                )
            }
        }, floatingActionButtonPosition = FabPosition.Center
    ) { paddingvalues ->
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
                style = Typography.h6
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
                style = Typography.body2
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "Search",
                modifier = Modifier
                    .constrainAs(icSearch) {
                        top.linkTo(tvTitle.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(tvSubtitle.bottom)
                        height = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 24.dp)
            )
            LazyRow(
                modifier = Modifier.constrainAs(lazyRow) {
                    top.linkTo(tvSubtitle.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items(count = 5) {
                    HistoryItem("https://images.pexels.com/photos/15286/pexels-photo.jpg?cs=srgb&dl=pexels-luis-del-r%C3%ADo-15286.jpg&fm=jpg")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen()
    }
}