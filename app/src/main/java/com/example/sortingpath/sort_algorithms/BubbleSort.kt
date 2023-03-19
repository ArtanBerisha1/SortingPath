package com.example.sortingpath.sort_algorithms

import com.example.sortingpath.CustomPointF

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

    fun sort(arrayList: ArrayList<CustomPointF>){
        for (i in 0 until arrayList.size) {
            for (j in 0 until arrayList.size - 1) {
                arrayList[j].isSelected = true
                arrayList.forEach {
                    if (it.id != arrayList[j].id) it.isSelected = false
                }
                if (arrayList[j].pointF.y < arrayList[j + 1].pointF.y) {
                    val temp = arrayList[j].pointF.y
                    arrayList[j].pointF.y = arrayList[j + 1].pointF.y
                    arrayList[j + 1].pointF.y = temp
                }
            }
        }
    }
}