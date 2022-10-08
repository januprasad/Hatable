package com.github.hatable

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class UI(val items: SnapshotStateList<GridProps> = mutableStateListOf())

data class GridProps(val item: String, val isSelectedGridItem: Boolean = false)