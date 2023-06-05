package com.example.sortingpath.sort_algorithms

import com.example.sortingpath.CustomPointF
import kotlinx.coroutines.delay

class HeapSortV1 {

    suspend fun sort(arr: ArrayList<CustomPointF>) {
        val n = arr.size

        // Build heap (rearrange array)
        for (i in n / 2 - 1 downTo 0) {
            heapify(arr, n, i)
        }

        // One by one extract an element from heap
        for (i in n - 1 downTo 1) {
            // Move current root to end
            val temp = arr[0].pointF.y
            arr[0].pointF.y = arr[i].pointF.y
            arr[i].pointF.y = temp
            delay(10)
            // Call heapify on the reduced heap
            heapify(arr, i, 0)
        }
    }

    private suspend fun heapify(arr: ArrayList<CustomPointF>, n: Int, i: Int) {
        var largest = i // Initialize largest as root
        val left = 2 * i + 1 // Left child
        val right = 2 * i + 2 // Right child

        // If left child is larger than root
        if (left < n && arr[left].pointF.y < arr[largest].pointF.y) {
            largest = left
        }

        // If right child is larger than largest so far
        if (right < n && arr[right].pointF.y < arr[largest].pointF.y) {
            largest = right
        }

        // If largest is not root
        if (largest != i) {
            val swap = arr[i].pointF.y
            arr[i].pointF.y = arr[largest].pointF.y
            arr[largest].pointF.y = swap

            delay(10)
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest)
        }
    }
}