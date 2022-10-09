package com.github.hatable

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

fun uniqueId() = (1..10000).shuffled().random()

@Stable
data class UI(val items: SnapshotStateList<GridProps> = mutableStateListOf())

data class GridProps(
    var item: String,
    var isSelectedGridItem: Boolean = false,
    val id: Int = uniqueId(),
    var isVisible: Boolean = true
)
