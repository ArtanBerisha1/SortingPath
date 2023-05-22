package com.example.sortingpath.sort_algorithms.merge_sort

import com.example.sortingpath.CustomPointF

// modifying array
class MergeSortCustomV2 {

    fun mergeSort(list: ArrayList<CustomPointF>) {
        if (list.size <= 1) {
            return
        }
        val middle = list.size / 2

        var size = 1

        val left = arrayListOf<CustomPointF>()
        val right = arrayListOf<CustomPointF>()
        for (i in 0 until list.size) {
            if (i < middle) {
                left.add(list[i])
            } else {
                right.add(list[i])
            }
        }

        mergeSort(left)
        mergeSort(right)

        merge(list, left, right)
    }

    fun merge(list: ArrayList<CustomPointF>, left: List<CustomPointF>, right: List<CustomPointF>) {
        var i = 0
        var j = 0
        val startIndex = list.indexOf(left[0])

        while (i < left.size && j < right.size) {
            if (left[i].pointF.y <= right[j].pointF.y) {
                i++
            } else {
                // Swap elements in the original list
                val tmp = list[startIndex+i].pointF.y
                list[startIndex+i].pointF.y = right[j].pointF.y
                right[j].pointF.y = tmp
                i++

                // Shift elements in the right list to maintain sorted order
                for (k in j+1 until right.size) {
                    if (right[k].pointF.y < right[k-1].pointF.y) {
                        val tmp2 = right[k].pointF.y
                        right[k].pointF.y = right[k-1].pointF.y
                        right[k-1].pointF.y = tmp2
                    } else {
                        break
                    }
                }
                j++
            }
        }

        // Copy remaining elements from right list into left list
        while (j < right.size) {
            list[startIndex+i+j] = right[j]
            j++

            // Shift elements in the left list to maintain sorted order
            for (k in left.size-1 downTo i+1) {
                if (left[k].pointF.y < left[k-1].pointF.y) {
                    val tmp = left[k].pointF.y
                    left[k].pointF.y = left[k-1].pointF.y
                    left[k-1].pointF.y = tmp
                } else {
                    break
                }
            }
        }
    }
}

fun main() {
    val firstArray = arrayListOf(38f, 27f, 43f, 3f, 9f, 82f, 10f)
//    for (i in 0 until 8) {
//        val randomVal = Random.nextFloat() * i
//        firstArray.add(randomVal)
//    }

    println(firstArray)

    val mergeSortV1 = MergeSortCustomV2()
//    mergeSortV1.mergeSort(firstArray)
    println(firstArray)
}