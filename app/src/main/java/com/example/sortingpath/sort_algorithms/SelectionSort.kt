package com.example.sortingpath.sort_algorithms

import com.example.sortingpath.CustomPointF
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelectionSort {
    fun sort(arrayList: ArrayList<CustomPointF>) {
        CoroutineScope(Dispatchers.Default).launch {
            val size = arrayList.size
            for (step in 0 until size - 1) {
                arrayList[step].isMainIndex = true
                arrayList.forEach {
                    if (it.id != arrayList[step].id) it.isMainIndex = false
                }
                var minIdx = step
                for (i in step + 1 until size) {
                    arrayList[i].isSecondIndex = true
                    arrayList.forEach {
                        if (it.id != arrayList[i].id) it.isSecondIndex = false
                    }
                    // Select the minimum element in each loop.
                    if (arrayList[i].pointF.y > arrayList[minIdx].pointF.y) {
                        minIdx = i
                    }
                    delay(10)
                }
                // put min at the correct position
                val temp = arrayList[step].pointF.y
                arrayList[step].pointF.y = arrayList[minIdx].pointF.y
                arrayList[minIdx].pointF.y = temp
            }
            arrayList.forEach {
                it.isSecondIndex = false
                it.isMainIndex = true
                delay(1)
            }
            delay(50)
            arrayList.forEach {
                it.isMainIndex = false
            }
        }
    }
}