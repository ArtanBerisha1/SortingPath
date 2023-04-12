package com.example.sortingpath.ui.theme

import android.app.Application
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sortingpath.MainViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val context = LocalContext.current

    val mainViewModel = remember {
        MainViewModel(context.applicationContext as Application)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .weight(0.9f),
            color = Color.LightGray,
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .pointerInteropFilter { motionEvent ->
                        mainViewModel.captureMotionPoints(motionEvent)
                        true
                    },
                onDraw = {
                    val canvasSize = this.size
                    for (x in 0 .. canvasSize.width.toInt() step 25) {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(x.toFloat(), 0f),
                            end = Offset(x.toFloat(), canvasSize.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    for (y in 0 .. canvasSize.height.toInt() step 25) {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, y.toFloat()),
                            end = Offset(canvasSize.width, y.toFloat()),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    mainViewModel.path.value?.let {
                        drawPath(
                            path = it,
                            color = Color.Black,
                            style = Stroke(
                                width = 4.dp.toPx()
                            )
                        )
                    }

                    if (mainViewModel.drawRectangles) {
                        for (i in 0 until  mainViewModel.listOfPoints.value.size) {
                            drawRect(
                                color = if (mainViewModel.listOfPoints.value[i].isMainIndex) {
                                    mainViewModel.playSound(i)
                                    Color.Green
                                } else if (mainViewModel.listOfPoints.value[i].isSecondIndex) {
                                    Color.Blue
                                } else {
                                    Color.White
                                },
                                topLeft = Offset(x = mainViewModel.listOfPoints.value[i].pointF.x, y = mainViewModel.listOfPoints.value[i].pointF.y),
                                size = Size(mainViewModel.rectWidth, canvasSize.height - mainViewModel.listOfPoints.value[i].pointF.y),
                                style = Fill
                            )
                            drawRect(
                                color = Color.Black,
                                topLeft = Offset(x = mainViewModel.listOfPoints.value[i].pointF.x, y = mainViewModel.listOfPoints.value[i].pointF.y),
                                size = Size(mainViewModel.rectWidth, canvasSize.height - mainViewModel.listOfPoints.value[i].pointF.y),
                                style = Stroke()
                            )
                        }
                        mainViewModel.path.value = null
                        mainViewModel.path.value = Path()
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.Top)
                .fillMaxHeight()
                .width(100.dp)
        ) {
            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                onClick = {
                    mainViewModel.clearDrawings()
                }
            ) {
                Text(text = "Clear")
            }

            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                onClick = {
                    mainViewModel.createRectanglesUnderPath()
                }
            ) {
                Text(text = "Process")
            }

            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                onClick = {
                    mainViewModel.sortRect()
                }
            ) {
                Text(text = "Sort")
            }
        }
    }
}