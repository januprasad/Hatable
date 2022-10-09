package com.github.hatable

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

fun uniqueId() = (1..1000).shuffled().first()

@Stable
data class UI(val items: SnapshotStateList<GridProps> = mutableStateListOf())

data class GridProps(
    var item: String,
    val isSelectedGridItem: Boolean = false,
    val id: Int = uniqueId(),
    var isVisible: Boolean = true
)
