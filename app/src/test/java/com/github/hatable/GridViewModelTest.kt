package com.github.hatable

import junit.framework.TestCase
import org.junit.Test

class GridViewModelTest {
    val gridViewModel = GridViewModel()

    @Test
    fun buildGridCollectedTest_Success() {
        var currentGridUiState = gridViewModel.uiState.value
        val firstItem = currentGridUiState.items.first()
        val lastItem = currentGridUiState.items.last()
        gridViewModel.onEvent(UIEvent.OnGridItemSelected(firstItem))
        gridViewModel.onEvent(UIEvent.OnSubmitPressed)
        val subList = gridViewModel.uiState.value.items.filter {
            it.isSelectedGridItem
        }
        TestCase.assertEquals(firstItem.item, subList.first().item)
    }

    @Test
    fun buildGridCollectedTest_Fail() {
        var currentGridUiState = gridViewModel.uiState.value
        val firstItem = currentGridUiState.items.first()
        val lastItem = currentGridUiState.items.last()
        gridViewModel.onEvent(UIEvent.OnGridItemSelected(firstItem))
        gridViewModel.onEvent(UIEvent.OnSubmitPressed)
        val subList = currentGridUiState.items.filter {
            it.isSelectedGridItem
        }
        TestCase.assertNotSame(lastItem.item, subList.first().item)
    }
}
