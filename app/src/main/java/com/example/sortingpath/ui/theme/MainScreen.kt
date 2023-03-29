package com.example.sortingpath.ui.theme

import android.graphics.PathMeasure
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
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
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sortingpath.CustomPointF
import com.example.sortingpath.sort_algorithms.BubbleSort

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val TAG = "MainActivity"

    val rectWidth = 20f

    val lastTouchX = remember {
        mutableStateOf(0f)
    }

    val lastTouchY = remember {
        mutableStateOf(0f)
    }

    val path = remember {
        mutableStateOf<Path?>(Path())
    }

    val listOfPoints = remember {
        mutableStateOf(ArrayList<CustomPointF>())
    }

    var drawRects by remember {
        mutableStateOf(false)
    }

    val bubbleSort = BubbleSort()

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
                        when (motionEvent.action) {
                            MotionEvent.ACTION_DOWN -> {
                                path.value?.moveTo(motionEvent.x, motionEvent.y)

                                lastTouchX.value = motionEvent.x
                                lastTouchY.value = motionEvent.y
                            }

                            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                                val historySize = motionEvent.historySize
                                for (i in 0 until historySize) {
                                    val historicalX = motionEvent.getHistoricalX(i)
                                    val historicalY = motionEvent.getHistoricalY(i)

                                    path.value?.lineTo(historicalX, historicalY)
                                }

                                path.value?.lineTo(motionEvent.x, motionEvent.y)
                                lastTouchX.value = motionEvent.x
                                lastTouchY.value = motionEvent.y
                            }
                        }
                        lastTouchX.value = motionEvent.x
                        lastTouchY.value = motionEvent.y

                        val tempPath = path.value
                        path.value = null
                        path.value = tempPath

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
                    path.value?.let {
                        drawPath(
                            path = it,
                            color = Color.Black,
                            style = Stroke(
                                width = 4.dp.toPx()
                            )
                        )
                    }

                    if (drawRects) {
                        for (i in 0 until  listOfPoints.value.size) {
                            drawRect(
                                color = if (listOfPoints.value[i].isMainIndex) {
                                    Color.Green
                                } else if (listOfPoints.value[i].isSecondIndex) {
                                    Color.Blue
                                } else {
                                    Color.White
                                },
                                topLeft = Offset(x = listOfPoints.value[i].pointF.x, y = listOfPoints.value[i].pointF.y),
                                size = Size(rectWidth, canvasSize.height - listOfPoints.value[i].pointF.y),
                                style = Fill
                            )
                            drawRect(
                                color = Color.Black,
                                topLeft = Offset(x = listOfPoints.value[i].pointF.x, y = listOfPoints.value[i].pointF.y),
                                size = Size(rectWidth, canvasSize.height - listOfPoints.value[i].pointF.y),
                                style = Stroke()
                            )
                        }
                        path.value = null
                        path.value = Path()
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
                    path.value = null
                    path.value = Path()
                    listOfPoints.value = arrayListOf()
                    drawRects = false
                }
            ) {
                Text(text = "Clear")
            }

            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                onClick = {
                    drawRects = !drawRects

                    // Create a new PathMeasure object
                    val pathMeasure = PathMeasure(path.value?.asAndroidPath(), false)

                    // Get the length of the path
                    val pathLength = pathMeasure.length

                    // Create an array to hold the coordinates of the points
                    val pointCoordinates = FloatArray(2)

                    var id = 0

                    // Loop through the path and get the coordinates of each point
                    for (distance in 0L..pathLength.toLong()) {
                        // Get the coordinates of the point at the current distance
                        pathMeasure.getPosTan(distance.toFloat(), pointCoordinates, null)

                        // Log the coordinates of the point
                        Log.d("Point Coordinates", "X: ${pointCoordinates[0]} Y: ${pointCoordinates[1]}")

                        if (listOfPoints.value.isEmpty()) {
                            listOfPoints.value.add(CustomPointF(pointF = PointF(pointCoordinates[0], pointCoordinates[1]), id = id))
                            id++
                        } else if (listOfPoints.value.last().pointF.x + rectWidth < pointCoordinates[0]) {
                            listOfPoints.value.add(CustomPointF(pointF = PointF(pointCoordinates[0], pointCoordinates[1]), id = id))
                            id++
                        }
                    }
                    Log.d(TAG, "DefaultPreview: ${listOfPoints.value}")
                }
            ) {
                Text(text = "Process")
            }

            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                onClick = {
                    Log.d(TAG, "DefaultPreview Before: ${listOfPoints.value}")
                    bubbleSort.sort(listOfPoints.value)
                    path.value = null
                    path.value = Path()
                    Log.d(TAG, "DefaultPreview After: ${listOfPoints.value}")
                }
            ) {
                Text(text = "Sort")
            }
        }
    }
}