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

    val limit = 6
    private var _uiState = mutableStateOf(UI())
    val itemsState: MutableState<UI> = _uiState
    val data: List<GridProps> = mutableListOf(
        GridProps(
            "Mon, Tue & Thu <br><b>09.00 - 10.00</b>"
        ),
        GridProps(
            "Mon, Wed & Thu <br><b>11.00 - 12.00</b>"
        ),
        GridProps(
            "Fri, Tue & Thu <br><b>11.00 - 12.00</b>"
        ),
        GridProps(
            "Tue & Thu <br><b>02.00 - 04.00</b>"
        ),
        GridProps(
            "Mon, Tue & Thu <br><b>09.00 - 10.00</b>"
        ),
        GridProps(
            "Mon, Wed & Thu <br><b>11.00 - 12.00</b>"
        ),
        GridProps(
            "Fri, Tue & Thu <br><b>11.00 - 12.00</b>"
        ),
        GridProps(
            "Tue & Thu <br><b>02.00 - 04.00</b>"
        ),
        GridProps(
            "Mon, Tue & Thu <br><b>09.00 - 10.00</b>"
        ),
        GridProps(
            "Mon, Wed & Thu <br><b>11.00 - 12.00</b>"
        ),
        GridProps(
            "Fri, Tue & Thu <br><b>11.00 - 12.00</b>"
        ),
        GridProps(
            "Tue & Thu <br><b>02.00 - 04.00</b>"
        ), GridProps(
            "Tue & Thu <br><b>02.00 - 04.00</b>"
        ),
        GridProps(
            "Mon, Tue & Thu <br><b>09.00 - 10.00</b>"
        ),
        GridProps(
            "Mon, Wed & Thu <br><b>11.00 - 12.00</b>"
        ),
        GridProps(
            "Fri, Tue & Thu <br><b>11.00 - 12.00</b>"
        ),
        GridProps(
            "Tue & Thu <br><b>02.00 - 04.00</b>"
        )
    )

    init {
        buildGridItems()
    }

    private fun buildGridItems() {
        _uiState.value.items += data
        if (_uiState.value.items.size > limit) {
            _uiState.value.items.takeLast(data.size - limit).map {
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
        val position = list.indexOfFirst { element ->
            element.item == item.item
        }
        list[position] = list[position].copy(
            isSelectedGridItem = !item.isSelectedGridItem
        )
    }

    fun showHideItems() {
        _uiState.value = _uiState.value.copy(
            items = list.toMutableStateList().also {
                it.takeLast(data.size - 6).map { item ->
                    item.isVisible = !item.isVisible
                }
            }
        )
    }

    private
    var list = _uiState.value.items
}
