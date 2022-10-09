package com.github.hatable

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GridViewModel @Inject constructor() : ViewModel() {

    //    private var _uiState = mutableStateListOf<GridProps>()
//    val itemsState: List<GridProps> = _uiState
    private var _uiState = mutableStateOf(UI())
    val itemsState: MutableState<UI> = _uiState

    init {
        buildGridItems()
    }

    private fun buildGridItems() {
        _uiState.value.items += data
        when {
            _uiState.value.items.size > limit -> _uiState.value.items.changeVisibilityUIListData {
                it.isVisible = false
            }
        }
    }

    private fun changeVisibilityUIListData(value: Boolean = false) {
        _uiState.value.items.takeLast(data.size - limit).map {
            it.isVisible = value
        }
    }

    /**
     * This method will trigger on user interactions and the impact will be state change of the ui.
     */
    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.OnGridItemSelected -> {
                changeItemState(event.item)
            }

            UIEvent.OnSubmitPressed -> {
                val subList = list.filter {
                    it.isSelectedGridItem
                }
            }

            UIEvent.OnMoreIconPressed -> {
                showHideItems()
            }
        }
    }

    private fun changeItemState(item: GridProps) {
        _uiState.value = _uiState.value.copy(
            items = list.toMutableStateList().also {
                it.find { temp ->
                    temp.id == item.id
                }?.isSelectedGridItem = !item.isSelectedGridItem
            }
        )
    }

    fun showHideItems() {
        _uiState.value = _uiState.value.copy(
            items = list.toMutableStateList().also {
                it.changeVisibilityUIListData() {
                    it.isVisible = !it.isVisible
                }
            }
        )
    }

    private
    var list = _uiState.value.items
}
