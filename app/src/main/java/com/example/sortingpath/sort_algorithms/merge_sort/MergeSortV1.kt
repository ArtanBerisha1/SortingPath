package com.example.sortingpath.sort_algorithms.merge_sort

import com.example.sortingpath.CustomPointF
import kotlin.random.Random

class MergeSortV1 {
    private val TAG = "MergeSortV1"

    var helpingArray: ArrayList<Float>?= null

    // working version
    fun mergeSort(list: ArrayList<Float>) {
        if (list.size > 1) {
            val middle = list.size / 2
            println("mergeSort: middle: $middle")
            val left = arrayListOf<Float>()
            val right = arrayListOf<Float>()
            if (middle == 1) {
                for (i in 0 until list.size) {
                    if (i == 0) {
                        left.add(list[i])
                    } else {
                        right.add(list[i])
                    }
                }
            } else if (middle > 1) {
                for (i in 0 until list.size) {
                    if (i <= middle) {
                        left.add(list[i])
                    } else {
                        right.add(list[i])
                    }
                }
            }
//            println("mergeSort: left: $left")
//            println("mergeSort: right: $right")
            println("before merge: list: $list, left: $left, right: $right")

            mergeSort(left)
            mergeSort(right)

            merge(list, left, right)
        }
    }

    //     working version
    fun merge(list: ArrayList<Float>, left: ArrayList<Float>, right: ArrayList<Float>) {
        var i = 0
        var j = 0
        println("merge: list: $list, left: $left, right: $right")

        while (i < left.size && j < right.size) {
            if (left[i] <= right[j]) {
                list[i + j] = left[i]
                i++
            } else {
                list[i + j] = right[j]
                j++
            }
        }

//        println("merge 1: list: $list, left: $left, right: $right")

        while (i < left.size) {
            list[i + j] = left[i]
            i++
        }

//        println("merge 2: list: $list, left: $left, right: $right")

        while (j < right.size) {
            list[i + j] = right[j]
            j++
        }
//        println("merge 3: list: $list, left: $left, right: $right")
    }

//    fun mergeSort(list: ArrayList<CustomPointF>) {
//        if (list.size > 1) {
//            val middle = list.size / 2
////            println("middle: $middle")
//            val left = arrayListOf<CustomPointF>()
////            println("left: $left")
//            val right = arrayListOf<CustomPointF>()
//            for (i in 0 until list.size) {
//                if (i < middle) {
//                    left.add(list[i])
//                } else {
//                    right.add(list[i])
//                }
//            }
//            mergeSort(left)
//            mergeSort(right)
//
//            merge(list, left, right)
//        }
//    }
//
//    fun merge(list: ArrayList<CustomPointF>, left: List<CustomPointF>, right: List<CustomPointF>) {
//        var i = 0
//        var j = 0
//
//        val tempList = arrayListOf<CustomPointF>()
//
//        while (i < left.size && j < right.size) {
//            if (left[i].pointF.y <= right[j].pointF.y) {
////                val temp = list[i + j].pointF.y
////                list[i + j].pointF.y = left[i].pointF.y
////                right[j].pointF.y = temp
//
//                tempList.add(left[i])
//                i++
//            } else {
////                val temp = list[i + j].pointF.y
////                list[i + j].pointF.y = right[j].pointF.y
////                left[i].pointF.y = temp
//
//                tempList.add(right[j])
//                j++
//            }
//        }
//
//        while (i < left.size) {
////            val temp = list[i + j].pointF.y
////            list[i + j].pointF.y = left[i].pointF.y
////            left[i].pointF.y = temp
//
//            tempList.add(left[i])
//            i++
//        }
//
//        while (j < right.size) {
////            val temp = list[i + j].pointF.y
////            list[i + j].pointF.y = right[j].pointF.y
////            right[j].pointF.y = temp
//
//            tempList.add(right[j])
//            j++
//        }
//
//        for (i in 0 until list.size) {
//            list[i] = tempList[i]
//        }
//    }
}

fun main() {
    val firstArray = arrayListOf(38f, 27f, 43f, 3f, 9f, 82f, 10f)
//    for (i in 0 until 8) {
//        val randomVal = Random.nextFloat() * i
//        firstArray.add(randomVal)
//    }

    println(firstArray)

    val mergeSortV1 = MergeSortV1()
    mergeSortV1.helpingArray = firstArray
    mergeSortV1.mergeSort(firstArray)
    println(firstArray)
}