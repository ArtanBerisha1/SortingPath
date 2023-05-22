package com.example.sortingpath.sort_algorithms.merge_sort

class MergeSortV2 {

    fun mergeSort(list: ArrayList<Float>) {
        if (list.size <= 1) {
            return
        }
        val middle = list.size / 2

        val left = arrayListOf<Float>()
        val right = arrayListOf<Float>()
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

    fun merge(list: ArrayList<Float>, left: ArrayList<Float>, right: ArrayList<Float>) {
        var i = 0
        var j = 0
        val startIndex = list.indexOf(left[0])

        while (i < left.size && j < right.size) {
            if (left[i] <= right[j]) {
                i++
            } else {
                // Swap elements in the original list
                val tmp = list[startIndex+i]
                list[startIndex+i] = right[j]
                right[j] = tmp
                i++

                // Shift elements in the right list to maintain sorted order
                for (k in j+1 until right.size) {
                    if (right[k] < right[k-1]) {
                        val tmp2 = right[k]
                        right[k] = right[k-1]
                        right[k-1]= tmp2
                    } else {
                        break
                    }
                }
                j++
            }
        }

        // Copy remaining elements from right list into left list
        while (j < right.size && (startIndex+i+j) <= right.size) {
            list[startIndex+i+j] = right[j]
            j++

            // Shift elements in the left list to maintain sorted order
            for (k in left.size-1 downTo i+1) {
                if (left[k] < left[k-1]) {
                    val tmp = left[k]
                    left[k] = left[k-1]
                    left[k-1] = tmp
                } else {
                    break
                }
            }
        }
    }
}

fun main() {
    val firstArray = arrayListOf(38f, 27f, 43f, 3f, 9f, 82f, 10f, 7f)
    println(firstArray)
    val mergeSort = MergeSortV2()
    mergeSort.mergeSort(firstArray)
    println(firstArray)
}