package com.selayar.history.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.history.BuildConfig
import com.selayar.history.component.TextSelayar
import com.selayar.history.data.model.Wisata
import com.selayar.history.ui.theme.Typography
import com.example.history.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryItem(wisata: Wisata, scale: Int = 1, onItemClickListener: () -> Unit) {
    val widthItem = 220.dp * scale
    val heightItem = 480.dp * scale
    ConstraintLayout(
        Modifier
            .width(widthItem)
            .height(heightItem)
            .background(Color.White)
    ) {
        val (cardImage, tvTitle, tvSubtitle) = createRefs()
        val baseUrl = BuildConfig.BASE_IMAGE_URL
        AsyncImage(model = "${baseUrl}${wisata.foto}",
            contentDescription = "Image Destination",
            placeholder = painterResource(id = R.drawable.sample_image),
            modifier = Modifier
                .constrainAs(cardImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .height(380.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop)
        TextSelayar(
            text = "${wisata.nama_wisata}",
            style = Typography.body1.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .constrainAs(tvTitle) {
                    top.linkTo(cardImage.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 8.dp))
        TextSelayar(
            text = "${wisata.deskripsi}",
            maxlines = 3,
            style = Typography.body1,
            modifier = Modifier
                .constrainAs(tvSubtitle) {
                    top.linkTo(tvTitle.bottom, 8.dp)
                    start.linkTo(tvTitle.start)
                    end.linkTo(tvTitle.end)
                    bottom.linkTo(parent.bottom, 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewHistoryItem() {
    HistoryItem(Wisata(-1, "", "", "", "", ""), 1, {})
}