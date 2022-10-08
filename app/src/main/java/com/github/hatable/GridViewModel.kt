package com.github.hatable

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
        val items: List<GridProps> = mutableListOf(
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
                "Wed & Thu <br><b>04.00 - 5.00</b>"
            )
        )
        _uiState.value.items.addAll(items)
    }

    /**
     * This method will trigger on user interactions and the impact will be state change of the ui.
     */
    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.OnGridItemSelected -> {
                changeItemState(event.item)
            }
        }
    }

    private fun changeItemState(item: GridProps) {
        val position = getList().indexOfFirst { element ->
            element.item == item.item
        }
        getList()[position] = getList()[position].copy(
            isSelectedGridItem = !item.isSelectedGridItem
        )
    }

    private fun getList() = _uiState.value.items
}
