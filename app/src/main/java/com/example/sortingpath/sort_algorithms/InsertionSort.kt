package com.example.sortingpath.sort_algorithms

import com.example.sortingpath.CustomPointF
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InsertionSort {
    fun sort(arrayList: ArrayList<CustomPointF>, speed: Int = 1) {
        CoroutineScope(Dispatchers.Default).launch {
            val size = arrayList.size
            for (step in 1 until size) {
                arrayList[step].isMainIndex = true
                arrayList.forEach {
                    if (it.id != arrayList[step].id) it.isMainIndex = false
                }
                val key = arrayList[step].pointF.y
                var i = step - 1
                // Compare key with each element on the left of it until an element smaller than
                // it is found.
                while (i >= 0 && key > arrayList[i].pointF.y) {
                    arrayList[i].isSecondIndex = true
                    arrayList.forEach {
                        if (it.id != arrayList[i].id) it.isSecondIndex = false
                    }
                    arrayList[i + 1].pointF.y = arrayList[i].pointF.y
                    --i
                    delay(30L / speed)
                }

                arrayList[i + 1].pointF.y = key
            }
            arrayList.forEach {
                it.isSecondIndex = false
                it.isMainIndex = true
                delay(1)
            }
            delay(100L / speed)
            arrayList.forEach {
                it.isMainIndex = false
            }
        }
    }
}