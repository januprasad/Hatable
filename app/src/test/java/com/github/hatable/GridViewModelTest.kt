package com.github.hatable

import junit.framework.TestCase
import org.junit.Test

class GridViewModelTest {
    val gridViewModel = GridViewModel()

    @Test
    fun buildGridCollectedTest_Success() {
        val currentGridUiState = gridViewModel.uiState.value
        val randomItem = currentGridUiState.items.random()
        gridViewModel.onEvent(UIEvent.OnGridItemSelected(randomItem))
        gridViewModel.onEvent(UIEvent.OnSubmitPressed)
        val subList = currentGridUiState.items.filter {
            it.isSelectedGridItem
        }
        TestCase.assertEquals(randomItem.item, subList.first().item)
    }

    @Test
    fun buildGridCollectedTest_Fail() {
        val currentGridUiState = gridViewModel.uiState.value
        gridViewModel.onEvent(UIEvent.OnSubmitPressed)
        val subList = currentGridUiState.items.filter {
            it.isSelectedGridItem
        }
        TestCase.assertEquals(subList.size, 0)
    }

    @Test
    fun buildGridMoreOptionTest_Success() {
//        var currentGridUiState = gridViewModel.uiState.value
//        gridViewModel.onEvent(UIEvent.OnMoreIconPressed)
//        TestCase.assertNotSame(lastItem.item, subList.first().item)
    }
}
