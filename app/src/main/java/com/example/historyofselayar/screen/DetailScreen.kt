package com.example.historyofselayar.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.historyofselayar.R
import com.example.historyofselayar.component.TextSelayar
import com.example.historyofselayar.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(urlImage: String, navController: NavController) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (ivItem, modal) = createRefs()
        var showModal by remember { mutableStateOf(false) }
        val modalState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true,
            confirmStateChange = { true }
        )
        val scope = rememberCoroutineScope()

        AsyncImage(
            model = urlImage,
            contentDescription = "Image Destination",
            placeholder = painterResource(id = R.drawable.sample_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
                .constrainAs(ivItem) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        change.consume()
                        val y = dragAmount
                        when {
                            y > 0 -> {
                                scope.launch {
                                    if (modalState.currentValue == ModalBottomSheetValue.Expanded) {
                                        modalState.hide()
                                    }
                                }
                            }
                            y < 0 -> {
                                scope.launch {
                                    if (modalState.currentValue == ModalBottomSheetValue.Hidden) {
                                        modalState.show()
                                    }
                                }
                            }
                        }
                    }
                },
        )

        if (modalState.currentValue == ModalBottomSheetValue.Hidden) {
            showModal = false
        }

        Log.d("TAG", "DetailScreen: $showModal")

        if (true) {
            ModalBottomSheetLayout(
                sheetContent = {
                    ConstraintLayout(
                        modifier = Modifier
                            .wrapContentSize()
                            .defaultMinSize(minHeight = 200.dp)
                            .background(Color.White)
                    ) {
                        val (tvTitle, tvSubtitle) = createRefs()
                        TextSelayar(
                            text = "Gong Nekara",
                            modifier = Modifier.constrainAs(tvTitle) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            },
                            style = Typography.body2.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                    }
                },
                modifier = Modifier.constrainAs(modal) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                sheetBackgroundColor = Color.White,
                content = {},
                sheetState = modalState
            )
        }
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(urlImage = "", rememberNavController())
}