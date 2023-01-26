package com.selayar.history.screen

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.selayar.history.utils.BarcodeAnalyzer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.util.concurrent.Executors
import com.example.history.R
import com.selayar.history.main.Screen
import com.selayar.history.viewmodel.HistoryViewModel

@SuppressLint("RestrictedApi")
@Composable
fun ScannerScreen(navController: NavController, historyViewModel: HistoryViewModel, lifecycleOwner: LifecycleOwner) {
    val context = LocalContext.current
    val lifecycleOwnerScreen = LocalLifecycleOwner.current
    var flashlight by remember { mutableStateOf(false) }
    var qrcode by remember { mutableStateOf("") }

//
//    SideEffect(effect = {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_RESUME) {
//                permissionState.launchPermissionRequest()
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//    })

    RequiredPermission(
        listPermission = arrayListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ),
        permissionGranted = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.54f))
            ) {
                val lensFacing = CameraSelector.LENS_FACING_BACK
                val lifecycleOwner = LocalLifecycleOwner.current

                Log.d("TAG", "ScannerScreen: lifecycle ${lifecycleOwnerScreen == lifecycleOwner}")

                val preview = Preview.Builder().build()
                val previewView = remember { PreviewView(context) }
                val cameraExecutor = Executors.newSingleThreadExecutor()
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                val cameraProvider = cameraProviderFuture.get()
                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(lensFacing)
                    .build()

                val barcodeAnalyzer = BarcodeAnalyzer() {
                    it.forEach {
                        qrcode = it.rawValue.toString()
                    }
                    navController.navigate("${Screen.DETAILSCREEN.name}/${qrcode}")
                }
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)
                    .build()
                    .also { it.setAnalyzer(cameraExecutor, barcodeAnalyzer) }

                AndroidView(
                    modifier = Modifier.fillMaxSize(),
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
                        cameraProviderFuture.addListener({
                            var camera: Camera?
                            preview.also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                                it.camera?.cameraControl?.enableTorch(flashlight)
                            }
                            cameraProvider.apply {
                                unbindAll()
                                camera = bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview,
                                    imageAnalysis
                                )
                            }
                            Log.d("TAG", "ScannerScreen: ${camera?.cameraInfo?.hasFlashUnit()}")
                            camera?.cameraControl?.enableTorch(flashlight)
                        }, ContextCompat.getMainExecutor(context))
                    }
                )

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.54f))
                ) {
                    val (icBarcodeArea, flash) = createRefs()

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

                    Icon(
                        painter = painterResource(id = if (flashlight) R.drawable.ic_baseline_flash_on_24 else R.drawable.ic_baseline_flash_off_24),
                        contentDescription = "Flash",
                        modifier = Modifier
                            .clickable {
                                flashlight = !flashlight
                            }
                            .clip(RoundedCornerShape(4.dp))
                            .constrainAs(flash) {
                                top.linkTo(parent.top, 16.dp)
                                end.linkTo(parent.end, 16.dp)
                            },
                        tint = Color.White
                    )
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