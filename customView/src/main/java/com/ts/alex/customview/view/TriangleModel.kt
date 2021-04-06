package com.ts.alex.customview.view

import android.graphics.Rect

data class TriangleModel(
    val text: String,
    val coord: Coordinates,
    val state: State
)

enum class State{
    QUANT_CREAM, QUANT_MILK, QUANT_SKIM, FAT_CREAM, FAT_MILK, FAT_SKIM
}
