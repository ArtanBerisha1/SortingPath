package com.example.sortingpath.sort_algorithms

import android.graphics.PointF

class BubbleSort {

//    fun sort(arrayList: ArrayList<Float>) {
//        for (i in 0 until arrayList.size) {
//            for (j in 0 until arrayList.size - 1) {
//                if (arrayList[j] > arrayList[j + 1]) {
//                    val temp = arrayList[j]
//                    arrayList[j] = arrayList[j + 1]
//                    arrayList[j + 1] = temp
//                }
//            }
//        }
//    }

    fun sort(arrayList: ArrayList<PointF>): ArrayList<PointF> {
        val currentArray = ArrayList(arrayList)
        for (i in 0 until arrayList.size) {
            for (j in 0 until arrayList.size - 1) {
                if (arrayList[j].y < arrayList[j + 1].y) {
                    val temp = arrayList[j].y
                    arrayList[j].y = arrayList[j + 1].y
                    arrayList[j + 1].y = temp
                }
            }
        }
        return currentArray
    }
}