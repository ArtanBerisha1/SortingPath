package com.example.sortingpath.sort_algorithms

class HeapSort {

    fun sort(arr: ArrayList<Int>) {
        val n = arr.size

        // Build heap (rearrange array)
        for (i in n / 2 - 1 downTo 0) {
            heapify(arr, n, i)
        }

        // One by one extract an element from heap
        for (i in n - 1 downTo 1) {
            // Move current root to end
            val temp = arr[0]
            arr[0] = arr[i]
            arr[i] = temp

            // Call heapify on the reduced heap
            heapify(arr, i, 0)
        }
    }

    fun heapify(arr: ArrayList<Int>, n: Int, i: Int) {
        var largest = i // Initialize largest as root
        val left = 2 * i + 1 // Left child
        val right = 2 * i + 2 // Right child

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left
        }

        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right
        }

        // If largest is not root
        if (largest != i) {
            val swap = arr[i]
            arr[i] = arr[largest]
            arr[largest] = swap

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest)
        }
    }
}

fun main() {
    val heapSort = HeapSort()
    val arr = arrayListOf(12, 11, 13, 5, 6, 7)
    heapSort.sort(arr)
    println("Sorted array:")
    println(arr)
}