package com.example.historyofselayar.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.historyofselayar.R
import com.example.historyofselayar.component.TextSelayar
import com.example.historyofselayar.ui.theme.Typography

@Composable
fun HistoryItem(urlImage: String) {
    ConstraintLayout(
        Modifier
            .wrapContentSize()
            .background(Color.White)
            .padding(horizontal = 12.dp)
    ) {
        val (cardImage, tvTitle, tvSubtitle) = createRefs()
        AsyncImage(model = urlImage,
            contentDescription = "Image Destination",
            placeholder = painterResource(id = R.drawable.sample_image),
            modifier = Modifier
                .constrainAs(cardImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(380.dp)
                .width(220.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop)
        TextSelayar(
            text = "Hutan Belantara",
            style = Typography.body2.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.constrainAs(tvTitle) {
                top.linkTo(cardImage.bottom, 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }.padding(horizontal = 8.dp))
        TextSelayar(
            text = stringResource(id = R.string.subtitle),
            maxlines = 3,
            style = Typography.body1.copy(fontSize = 10.sp),
            modifier = Modifier.constrainAs(tvSubtitle) {
                top.linkTo(tvTitle.bottom)
                start.linkTo(tvTitle.start)
                end.linkTo(tvTitle.end)
                bottom.linkTo(parent.bottom, 8.dp)
                width = Dimension.fillToConstraints
            }.padding(horizontal = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewHistoryItem() {
    HistoryItem("https://image-sample.com")
}