package com.example.sortingpath

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sortingpath.ui.theme.SortingPathTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SortingPathTheme {
                DefaultPreview()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val TAG = "MainActivity"

    val lastTouchX = remember {
        mutableStateOf(0f)
    }

    val lastTouchY = remember {
        mutableStateOf(0f)
    }

    val path = remember {
        mutableStateOf<Path?>(Path())
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
                                    Log.d(TAG, "DefaultPreview: x: $historicalX, y: $historicalY")

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

                    Log.d(TAG, "DefaultPreview: size: ${this.size}")
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
                }
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.Top)
                .fillMaxHeight()
        ) {
            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .width(90.dp),
                onClick = {
                    path.value = null
                    path.value = Path()
                }
            ) {
                Text(text = "Clear")
            }

            Button(
                modifier = Modifier
                    .padding(2.dp),
                onClick = {}
            ) {
                Text(text = "Process")
            }
        }


    }


}