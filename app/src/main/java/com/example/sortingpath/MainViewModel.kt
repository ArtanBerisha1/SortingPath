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
import com.example.sortingpath.sort_algorithms.*
import com.example.sortingpath.sort_algorithms.merge_sort.MergeSortCustomV2
import com.example.sortingpath.sort_algorithms.merge_sort.MergeSortV1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val TAG = "MainViewModel"
    val rectWidth = 20f

    private val lastTouchX = mutableStateOf(0f)
    private val lastTouchY = mutableStateOf(0f)

    val path = mutableStateOf<Path?>(Path())
    val listOfPoints = mutableStateOf(ArrayList<CustomPointF>())

    var drawRectangles by mutableStateOf(false)

    private val bubbleSort = BubbleSort() // Done
    private val selectionSort = SelectionSort() // Done
    private val insertionSort = InsertionSort() // Done
    private val mergeSortV2 = MergeSortCustomV2() // Progress
    private val heapSort = HeapSortV1() // Progress

    private val testMergeSortV1 = MergeSortV1()

    private var soundPool: SoundPool? = null
    private var firstSound: Int = 0

    private var counter: Int? = null
    private var soundPoolLoaded = false

    val algorithmList = arrayListOf("Bubble Sort", "Insertion Sort", "Selection Sort", "Heap Sort", "Merge Sort")
    private var currentAlgorithm: String? = null

    var drawSpeed = 1


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
        when (currentAlgorithm) {
            "Bubble Sort" -> bubbleSort.sort(listOfPoints.value, drawSpeed)
            "Insertion Sort" -> insertionSort.sort(listOfPoints.value, drawSpeed)
            "Selection Sort" -> selectionSort.sort(listOfPoints.value, drawSpeed)
            "Merge Sort" -> CoroutineScope(Dispatchers.Default).launch {
                do {
                    mergeSortV2.mergeSort(listOfPoints.value, drawSpeed)
                } while (!isArraySorted(listOfPoints.value))
            }
            "Heap Sort" -> CoroutineScope(Dispatchers.Default).launch {
                if (!isArraySorted(listOfPoints.value)) heapSort.sort(listOfPoints.value, drawSpeed)
            }
            else -> {}
        }
        path.value = null
        path.value = Path()
        Log.d(TAG, "DefaultPreview After: ${listOfPoints.value}")
    }

    private fun isArraySorted(arrayList: ArrayList<CustomPointF>): Boolean {
        for (i in 0 until arrayList.size - 1) {
            if (arrayList[i].pointF.y < arrayList[i + 1].pointF.y) return false
        }
        return true
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

    fun updateCurrentAlgorithm(id: Int) {
        when(id) {
            0 -> currentAlgorithm = "Bubble Sort"
            1 -> currentAlgorithm = "Insertion Sort"
            2 -> currentAlgorithm = "Selection Sort"
            3 -> currentAlgorithm = "Heap Sort"
            4 -> currentAlgorithm = "Merge Sort"
        }
    }

    fun canWeSort() = currentAlgorithm != null

    override fun onCleared() {
        super.onCleared()
        soundPool?.release()
        soundPool = null
    }
}