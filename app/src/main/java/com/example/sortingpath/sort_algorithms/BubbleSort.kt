package com.example.sortingpath.sort_algorithms

import com.example.sortingpath.CustomPointF
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BubbleSort {
    fun sort(arrayList: ArrayList<CustomPointF>){
        CoroutineScope(Dispatchers.Default).launch {
            for (i in 0 until arrayList.size) {
                arrayList[i].isMainIndex = true
                arrayList.forEach {
                    if (it.id != arrayList[i].id) it.isMainIndex = false
                }
                for (j in 0 until arrayList.size - i - 1) {
                    arrayList[j].isSecondIndex = true
                    arrayList.forEach {
                        if (it.id != arrayList[j].id) it.isSecondIndex = false
                    }
                    if (arrayList[j].pointF.y < arrayList[j + 1].pointF.y) {
                        val temp = arrayList[j].pointF.y
                        arrayList[j].pointF.y = arrayList[j + 1].pointF.y
                        arrayList[j + 1].pointF.y = temp
                    }
                    delay(3)
                }
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