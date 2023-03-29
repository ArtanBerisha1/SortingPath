package com.example.sortingpath

import android.graphics.PointF

data class CustomPointF(
    var id: Int,
    var pointF: PointF,
    var isMainIndex: Boolean = false,
    var isSecondIndex: Boolean = false
)