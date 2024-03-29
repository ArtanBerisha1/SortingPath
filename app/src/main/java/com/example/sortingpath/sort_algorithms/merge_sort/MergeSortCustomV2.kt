package com.example.sortingpath.sort_algorithms.merge_sort

import com.example.sortingpath.CustomPointF
import kotlinx.coroutines.delay

class MergeSortCustomV2 {
    private var drawSpeed = 1

    suspend fun mergeSort(list: ArrayList<CustomPointF>, speed: Int) {
        drawSpeed = speed
        if (list.size <= 1) {
            return
        }
        val middle = list.size / 2

        val left = arrayListOf<CustomPointF>()
        val right = arrayListOf<CustomPointF>()
        for (i in 0 until list.size) {
            if (i < middle) {
                left.add(list[i])
            } else {
                right.add(list[i])
            }
        }
        mergeSort(left, drawSpeed)
        mergeSort(right, drawSpeed)

        merge(list, left, right)
    }

    private suspend fun merge(list: ArrayList<CustomPointF>, left: List<CustomPointF>, right: List<CustomPointF>) {
        var i = 0
        var j = 0
        val startIndex = list.indexOf(left[0])

        while (i < left.size && j < right.size) {
            if (left[i].pointF.y >= right[j].pointF.y) {
                i++
            } else {
                // Swap elements in the original list
                val tmp = list[startIndex + i].pointF.y
                list[startIndex + i].pointF.y = right[j].pointF.y
                right[j].pointF.y = tmp
                i++
                delay(30L / drawSpeed)

                // Shift elements in the right list to maintain sorted order
                for (k in j + 1 until right.size) {
                    if (right[k].pointF.y > right[k - 1].pointF.y) {
                        val tmp2 = right[k].pointF.y
                        right[k].pointF.y = right[k - 1].pointF.y
                        right[k - 1].pointF.y = tmp2
                        delay(30L / drawSpeed)
                    } else {
                        break
                    }
                }
                j++
            }
        }

        // Copy remaining elements from right list into left list
        while (j < right.size) {
            list[startIndex + i + j] = right[j]
            j++

            // Shift elements in the left list to maintain sorted order
            for (k in left.size - 1 downTo i + 1) {
                if (left[k].pointF.y > left[k - 1].pointF.y) {
                    val tmp = left[k].pointF.y
                    left[k].pointF.y = left[k - 1].pointF.y
                    left[k - 1].pointF.y = tmp
                    delay(30L / drawSpeed)
                } else {
                    break
                }
            }
        }
    }
}