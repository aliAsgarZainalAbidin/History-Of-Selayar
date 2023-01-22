package com.example.historyofselayar.screen

import android.Manifest
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraXConfig
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.navigation.NavController
import com.example.historyofselayar.component.TextSelayar
import com.example.historyofselayar.utils.BarcodeAnalyzer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.util.concurrent.Executors
import com.example.historyofselayar.R
import com.example.historyofselayar.viewmodel.HistoryViewModel

@Composable
fun ScannerScreen(navController: NavController,historyViewModel: HistoryViewModel) {
    RequiredPermission(
        listPermission = arrayListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ),
        permissionGranted = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.54f))
            ) {
                val context = LocalContext.current
                val lensFacing = CameraSelector.LENS_FACING_BACK
                val lifeCycleOwner = LocalLifecycleOwner.current

                val preview = Preview.Builder().build()
                val previewView = remember { PreviewView(context) }
                val cameraExecutor = Executors.newSingleThreadExecutor()
                val cameraProvider = ProcessCameraProvider.getInstance(context).get()
                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(lensFacing)
                    .build()

                val barcodeAnalyzer = BarcodeAnalyzer() {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
//                    TODO("Navigate To Detail")
                }
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { it.setAnalyzer(cameraExecutor, barcodeAnalyzer) }

                AndroidView(
                    factory = {
                        previewView.apply {
                            this.scaleType = PreviewView.ScaleType.FILL_CENTER
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        }
                    },
                    update = {
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                        cameraProvider.apply {
                            unbindAll()
                            bindToLifecycle(lifeCycleOwner, cameraSelector, preview, imageAnalysis)
                        }
                    }
                )

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.54f))
                ) {
                    val icBarcodeArea = createRef()

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .constrainAs(icBarcodeArea) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_barcode_area),
                            contentDescription = "Scanner Area",
                            tint = Color.White,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.White.copy(0.2f))
                        )
                    }
                }
            }
        },
        permissionRequired = {

        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequiredPermission(
    listPermission: ArrayList<String> = arrayListOf(),
    permissionGranted: @Composable () -> Unit,
    permissionRequired: () -> Unit = {}
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listPermission
    )
    if (permissionState.allPermissionsGranted) {
        permissionGranted()
    } else {
        permissionRequired()
        LaunchedEffect(key1 = Unit) {
            permissionState.launchMultiplePermissionRequest()
        }
    }
}