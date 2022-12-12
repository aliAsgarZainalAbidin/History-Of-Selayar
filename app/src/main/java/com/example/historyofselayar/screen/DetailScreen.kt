package com.example.historyofselayar.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        val modalState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.HalfExpanded,
            skipHalfExpanded = false,
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
                            y > 0 -> scope.launch { modalState.animateTo(ModalBottomSheetValue.HalfExpanded) }
                            y < 0 -> scope.launch {
                                if (modalState.currentValue == ModalBottomSheetValue.Hidden) {
                                    modalState.animateTo(ModalBottomSheetValue.HalfExpanded)
                                } else modalState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }
                    }
                },
        )

        ModalBottomSheetLayout(
            sheetContent = {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(12.dp)
                        .verticalScroll(rememberScrollState())
                        .defaultMinSize(32.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.95f)
                        .background(Color.White)
                ) {
                    val (tvName, tvImage, tvLocation, tvDeskripsi, lazyRow) = createRefs()
                    TextSelayar(
                        text = "Hutan Belantara",
                        style = Typography.body2.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.constrainAs(tvName) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                        overflow = TextOverflow.Ellipsis
                    )
                    TextSelayar(
                        text = "Majene, Kalimantan Barat",
                        style = Typography.body1.copy(
                            fontSize = 10.sp,
                            color = Color.Black.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.constrainAs(tvLocation) {
                            top.linkTo(tvName.bottom, 4.dp)
                            start.linkTo(tvName.start)
                            end.linkTo(tvName.end)
                            width = Dimension.fillToConstraints
                        },
                        overflow = TextOverflow.Ellipsis
                    )
                    TextSelayar(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Imperdiet et at neque interdum. Malesuada et at magna quam aenean mus tristique ut. Varius erat duis amet in diam sed viverra sed malesuada. Aliquam cras non viverra dolor nulla nibh in. Orci nulla scelerisque erat eget cursus at. Congue quis dolor mattis mauris arcu. Senectus fusce morbi lorem in. Adipiscing vitae fermentum erat nulla massa felis feugiat vitae, netus. Integer natoque volutpat tincidunt quam mauris, a purus molestie. Amet consequat, sed venenatis leo lacus, varius ac lectus et. Porta pellentesque velit diam tortor ultrices urna. At vel quis in non massa proin elementum eleifend vehicula. Amet arcu ut tristique quis sagittis enim et. Tortor, tempus ac rutrum amet hendrerit et mauris vulputate. ",
                        style = Typography.body1.copy(fontSize = 10.sp),
                        modifier = Modifier.constrainAs(tvDeskripsi) {
                            top.linkTo(tvLocation.bottom, 8.dp)
                            start.linkTo(tvName.start)
                            end.linkTo(tvName.end)
                            width = Dimension.fillToConstraints
                        },
                        maxlines = 90
                    )
                    LazyRow(
                        modifier = Modifier.constrainAs(lazyRow) {
                            top.linkTo(tvDeskripsi.bottom, 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, 8.dp)
                        }
                    ) {
                        items(5) {
                            AsyncImage(
                                model = urlImage,
                                contentDescription = "Image Destination",
                                placeholder = painterResource(id = R.drawable.sample_image),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(400.dp)
                                    .wrapContentWidth()
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }
            },
            modifier = Modifier.constrainAs(modal) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            content = {},
            sheetBackgroundColor = Color.White,
            sheetState = modalState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(urlImage = "", rememberNavController())
}