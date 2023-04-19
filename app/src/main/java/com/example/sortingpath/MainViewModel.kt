package com.example.sortingpath

import android.app.Application
import android.graphics.PathMeasure
import android.graphics.PointF
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import android.view.MotionEvent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sortingpath.sort_algorithms.BubbleSort
import com.example.sortingpath.sort_algorithms.SelectionSort
import kotlinx.coroutines.launch


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = "MainViewModel"
    val rectWidth = 20f

    private val lastTouchX = mutableStateOf(0f)
    private val lastTouchY = mutableStateOf(0f)

    val path = mutableStateOf<Path?>(Path())
    val listOfPoints = mutableStateOf(ArrayList<CustomPointF>())

    var drawRectangles by mutableStateOf(false)

    private val bubbleSort = BubbleSort()
    private val selectionSort = SelectionSort()

    private var soundPool: SoundPool? = null
    private var firstSound: Int = 0

    private var counter: Int? = null
    private var soundPoolLoaded = false

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(
                AudioAttributes.USAGE_GAME
            )
            .setContentType(
                AudioAttributes.CONTENT_TYPE_SONIFICATION
            )
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()
        soundPool?.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                soundPoolLoaded = true
            } else {
                Log.e(TAG, "SoundPool not loaded: ")
            }
        }
        firstSound = soundPool?.load(application, R.raw.bell, 1) ?: -1
    }


    fun createRectanglesUnderPath() {
        drawRectangles = !drawRectangles

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

            // Log the coordinates of the point, to be removed, for testing only
//            Log.d("Point Coordinates", "X: ${pointCoordinates[0]} Y: ${pointCoordinates[1]}")

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

    fun captureMotionPoints(motionEvent: MotionEvent) {
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
    }


    fun sortRect() {
        Log.d(TAG, "DefaultPreview Before: ${listOfPoints.value}")
//        bubbleSort.sort(listOfPoints.value)
        selectionSort.sort(listOfPoints.value)
        path.value = null
        path.value = Path()
        Log.d(TAG, "DefaultPreview After: ${listOfPoints.value}")
    }

    fun clearDrawings() {
        path.value = null
        path.value = Path()
        listOfPoints.value = arrayListOf()
        drawRectangles = false
    }

    fun playSound(currentCounter: Int) {
        viewModelScope.launch {
            if (counter == null) {
                counter = currentCounter
                if (soundPoolLoaded) {
                    soundPool?.play(firstSound, 1f, 1f, 0, 0, 1f)
                }
            } else {
                if (currentCounter != counter) {
                    counter = currentCounter
                    if (soundPoolLoaded) {
                        soundPool?.play(firstSound, 1f, 1f, 0, 0, 1f)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundPool?.release()
        soundPool = null
    }
}