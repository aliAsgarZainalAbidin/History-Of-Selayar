package com.selayar.history.screen

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.history.BuildConfig
import com.example.history.R
import com.selayar.history.component.TextSelayar
import com.selayar.history.ui.theme.Typography
import com.selayar.history.viewmodel.HistoryViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(id: Int, navController: NavController, historyViewModel: HistoryViewModel) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (ivItem, modal) = createRefs()
        val context = LocalContext.current
        val modalState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Expanded,
            skipHalfExpanded = false,
        )
        val scope = rememberCoroutineScope()
        val detailWisata by remember { historyViewModel.getDetailWisata(id) }
        val baseImageUrl = BuildConfig.BASE_IMAGE_URL

        val lat = detailWisata.wisata?.maps?.let { getLocation(it).getOrNull(0) }
        val long = detailWisata.wisata?.maps?.let { getLocation(it).getOrNull(1) }
        Log.d("TAG", "DetailScreen: $lat $long")


        AsyncImage(
            model = "$baseImageUrl${detailWisata.wisata?.foto}",
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
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .fillMaxHeight(0.95f)
                        .background(Color.White)
                ) {
                    val (tvName, tvImage, tvLocation,titleDeskripsi, tvDeskripsi, lazyRow) = createRefs()
                    TextSelayar(
                        text = detailWisata.wisata?.nama_wisata ?: "",
                        style = Typography.body1.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.constrainAs(tvName) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                        overflow = TextOverflow.Ellipsis
                    )
                    TextSelayar(
                        text = getAddress(context, lat?.toDouble(), long?.toDouble()),
                        style = Typography.body1.copy(
                            color = Color.Black.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.constrainAs(tvLocation) {
                            top.linkTo(tvName.bottom, 4.dp)
                            start.linkTo(tvName.start)
                            end.linkTo(tvName.end)
                            width = Dimension.fillToConstraints
                        },
                        overflow = TextOverflow.Ellipsis,
                        maxlines = 3
                    )
                    TextSelayar(
                        "Deskripsi",
                        style = Typography.body1.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.constrainAs(titleDeskripsi) {
                            top.linkTo(tvLocation.bottom, 16.dp)
                            start.linkTo(tvName.start)
                            end.linkTo(tvName.end)
                            width = Dimension.fillToConstraints
                        },
                    )
                    TextSelayar(
                        detailWisata.wisata?.deskripsi ?: "",
                        style = Typography.body1,
                        modifier = Modifier.constrainAs(tvDeskripsi) {
                            top.linkTo(titleDeskripsi.bottom, 8.dp)
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
                        itemsIndexed(detailWisata.attach ?: arrayListOf()) { index, value ->
                            AsyncImage(
                                model = "$baseImageUrl${value.attach_name}",
                                contentDescription = "Image Destination",
                                placeholder = painterResource(id = R.drawable.sample_image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(260.dp)
                                    .padding(12.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                alignment = Alignment.Center
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

private fun getAddress(context: Context, lat: Double? , long: Double? ): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val address = geocoder.getFromLocation(lat ?: -5.182083, long ?: 119.453222, 1)?.getOrNull(0)
    val addressLine = address?.getAddressLine(0)
    val kota = address?.locality
    val state = address?.adminArea
    val country = address?.countryName
    val street = address?.thoroughfare
    val result = "$street, $kota, $state, $country."
    Log.d("TAG", "getAddress: $addressLine \n $kota \n $state \n $country \n $street")
    return result
}

private fun getLocation(rawAddress: String): List<String> {
    val location = rawAddress.split('@').getOrNull(1)?.split(",")
    val address = arrayListOf<String>()
    location?.forEachIndexed { index, s ->
        if (index < 2){
            address.add(s)
        }
    }
    return address
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailScreen(-1, rememberNavController(), hiltViewModel())
}