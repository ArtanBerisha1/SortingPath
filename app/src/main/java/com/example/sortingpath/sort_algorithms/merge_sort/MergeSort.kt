package com.example.sortingpath.sort_algorithms.merge_sort

import com.example.sortingpath.CustomPointF
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MergeSort {

    var originalArrayList: ArrayList<CustomPointF>? = null

    // Merge two subarrays L and M into arr
    private suspend fun merge1(arrayList: ArrayList<CustomPointF>, p: Int, q: Int, r: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            // Create L ← A[p..q] and M ← A[q+1..r]
            val n1 = q - p + 1
            val n2 = r - q
            val L = FloatArray(n1)
            val M = FloatArray(n2)
            for (i in 0 until n1) L[i] = arrayList[p + i].pointF.y
            for (j in 0 until n2) M[j] = arrayList[q + 1 + j].pointF.y

            // Maintain current index of sub-arrays and main array
            var i: Int = 0
            var j: Int = 0
            var k: Int = p

            // Until we reach either end of either L or M, pick larger among
            // elements L and M and place them in the correct position at A[p..r]
            while (i < n1 && j < n2) {
                if (L[i] >= M[j]) {
                    arrayList[i].isMainIndex = true
                    arrayList.forEach {
                        if (it.id != arrayList[i].id) it.isMainIndex = false
                    }
                    val temp = arrayList[k].pointF.y
                    arrayList[k].pointF.y = L[i]
                    L[i] = temp
                    i++
                    delay(10)
                } else {
                    arrayList[j].isSecondIndex = true
                    arrayList.forEach {
                        if (it.id != arrayList[j].id) it.isSecondIndex = false
                    }
                    val temp = arrayList[k].pointF.y
                    arrayList[k].pointF.y = M[j]
                    M[j] = temp
                    j++
                    delay(10)
                }
                k++
            }

            // When we run out of elements in either L or M,
            // pick up the remaining elements and put in A[p..r]
            while (i < n1) {
                arrayList[i].isMainIndex = true
                arrayList.forEach {
                    if (it.id != arrayList[i].id) it.isMainIndex = false
                }
                val temp = arrayList[k].pointF.y
                arrayList[k].pointF.y = L[i]
                L[i] = temp
                i++
                k++
                delay(10)
            }
            while (j < n2) {
                arrayList[j].isSecondIndex = true
                arrayList.forEach {
                    if (it.id != arrayList[j].id) it.isSecondIndex = false
                }
                val temp = arrayList[k].pointF.y
                arrayList[k].pointF.y = M[j]
                M[j] = temp
                j++
                k++
                delay(10)
            }
        }
    }

    // Divide the array into two subarrays, sort them and merge them
    fun mergeSort(floatArrayList: ArrayList<Float>) {
        if (floatArrayList.size <= 1) return
        val length = floatArrayList.size

        val middle = length / 2
        val leftArrayList = arrayListOf<Float>()
        val rightArrayList = arrayListOf<Float>()

        val i = 0
        val j = 0

        for (index in i until length) {
            if (index < middle) {
                leftArrayList.add(floatArrayList[index])
            } else {
                rightArrayList.add(floatArrayList[index])
            }
        }

        mergeSort(leftArrayList)
        mergeSort(rightArrayList)
        originalArrayList?.let { merge(leftArrayList, rightArrayList, it) }
    }

    fun merge(leftArray: ArrayList<Float>, rightArray: ArrayList<Float>, originalArray: ArrayList<CustomPointF>) {
        val leftSize = originalArray.size / 2
        val rightSize = originalArray.size - leftSize

        var i = 0
        var l = 0
        var r = 0

        // check conditions for merging
        while (l < leftSize && r < rightSize) {
            if (leftArray[l] < rightArray[r]) {
                originalArray[i].pointF.y = leftArray[l]
                i++
                l++
            } else {
                originalArray[i].pointF.y = rightArray[r]
                i++
                r++
            }
        }

        while (l < leftSize) {
            originalArray[i].pointF.y = leftArray[l]
            i++
            l++
        }

        while (r < rightSize) {
            originalArray[i].pointF.y = rightArray[r]
            i++
            r++
        }
    }
}