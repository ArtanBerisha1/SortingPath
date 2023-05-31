package com.example.sortingpath.sort_algorithms.merge_sort


class MergeSortV1 {

    var helpingArray: ArrayList<Float> = arrayListOf()

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
                    if (i <= middle) {
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

        while (i < left.size && j < right.size) {
            if (left[i] <= right[j]) {
                list[i + j] = left[i]
                swapPlaces(i + j, i)
                i++
            } else {
                list[i + j] = right[j]
                swapPlaces(i + j, j)
                j++
            }
        }

        while (i < left.size) {
            list[i + j] = left[i]
            swapPlaces(i + j, i)
            i++
        }

        while (j < right.size) {
            list[i + j] = right[j]
            swapPlaces(i + j, j)
            j++
        }
    }

    private fun swapPlaces(first: Int, second: Int) {
        val firstVal = helpingArray[first]
        helpingArray[first] = helpingArray[second]
        helpingArray[second] = firstVal
    }
}

fun main() {
    val firstArray = arrayListOf(38f, 27f, 43f, 3f, 9f, 82f, 10f)
    val firstArray1 = arrayListOf(38f, 27f, 43f, 3f, 9f, 82f, 10f)

    println(firstArray)

    val mergeSortV1 = MergeSortV1()
    mergeSortV1.helpingArray = firstArray1
    mergeSortV1.mergeSort(firstArray)
    println("FINISH, firstArray: $firstArray")
    println("FINISH, helpingArray: ${mergeSortV1.helpingArray}")
}