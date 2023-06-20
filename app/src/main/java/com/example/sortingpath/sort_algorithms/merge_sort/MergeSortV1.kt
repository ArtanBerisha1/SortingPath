package com.example.sortingpath.sort_algorithms.merge_sort

import android.util.Log
import com.example.sortingpath.CustomPointF


class MergeSortV1 {

    var helpingArray: ArrayList<Float> = arrayListOf()
    var helpingArrayCustomPointF: ArrayList<CustomPointF> = arrayListOf()

    var mainIndex = 0

    // working version
    fun mergeSort(list: ArrayList<Float>) {
        println("CURRENT STATE: $helpingArray")
        if (list.size > 1) {
            val middle = list.size / 2
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
                    if (i < middle) {
                        left.add(list[i])
                    } else {
                        right.add(list[i])
                    }
                }
            }
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

        mainIndex = 0

        while (i < left.size && j < right.size) {
            if (left[i] <= right[j]) {
                list[i + j] = left[i]
                helpingArray[mainIndex] = left[i]
                mainIndex++
                i++
            } else {
                list[i + j] = right[j]
                helpingArray[mainIndex] = right[j]
                mainIndex++
                j++
            }
        }

        while (i < left.size) {
            list[i + j] = left[i]
            helpingArray[mainIndex] = left[i]
            mainIndex++
            i++
        }

        while (j < right.size) {
            list[i + j] = right[j]
            helpingArray[mainIndex] = right[j]
            mainIndex++
            j++
        }
    }
    //-------------------------

    suspend fun mergeSortCustomPointF(list: ArrayList<CustomPointF>) {
        Log.d("artan11", "mergeSortCustomPointF: original: $helpingArrayCustomPointF")
        if (list.size > 1) {
            val middle = list.size / 2
            val left = arrayListOf<CustomPointF>()
            val right = arrayListOf<CustomPointF>()

            for (i in 0 until middle) {
                left.add(list[i])
            }
            for (i in middle until list.size) {
                right.add(list[i])
            }
            mergeSortCustomPointF(left)
            mergeSortCustomPointF(right)

            mergeCustomPointF(list, left, right)
        }
    }

    private fun mergeCustomPointF(list: ArrayList<CustomPointF>, left: ArrayList<CustomPointF>, right: ArrayList<CustomPointF>) {
        var i = 0
        var j = 0
        var k = 0

        while (i < left.size && j < right.size) {
            if (left[i].pointF.y <= right[j].pointF.y) {
                list[k] = left[i]
//                helpingArrayCustomPointF[left[k].id].pointF.y = helpingArrayCustomPointF[left[i].id].pointF.y
                i++
            } else {
                list[k] = right[j]
//                helpingArrayCustomPointF[list[k].id].pointF.y = helpingArrayCustomPointF[right[j].id].pointF.y
                j++
            }
            k++
        }

        while (i < left.size) {
            list[k] = left[i]
//            helpingArrayCustomPointF[list[k].id].pointF.y = helpingArrayCustomPointF[left[i].id].pointF.y
            i++
            k++
        }

        while (j < right.size) {
            list[k] = right[j]
//            helpingArrayCustomPointF[list[k].id].pointF.y = helpingArrayCustomPointF[right[j].id].pointF.y
            j++
            k++
        }
    }
}