package com.github.hatable

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

typealias UiState = MutableState<UI>

@HiltViewModel
class GridViewModel @Inject constructor() : ViewModel() {

    private var _uiState = mutableStateOf(UI())
    val uiState: UiState = _uiState

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
                it.changeVisibilityUIListData() { item ->
                    item.isVisible = !item.isVisible
                }
            }
        )
    }

    private
    var list = _uiState.value.items
}
