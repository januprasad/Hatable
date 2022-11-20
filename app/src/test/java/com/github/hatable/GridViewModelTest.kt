package com.github.hatable

import junit.framework.TestCase
import org.junit.Test

class GridViewModelTest {
    val gridViewModel = GridViewModel()

    @Test
    fun buildGridCollectedTest_Fail() {
        gridViewModel.reinit()
        val currentGridUiState = gridViewModel.uiState.value
        gridViewModel.onEvent(UIEvent.OnSubmitPressed)
        val subList = currentGridUiState.items.filter {
            it.isSelectedGridItem
        }
        TestCase.assertEquals(subList.size, 0)
    }

    @Test
    fun buildGridCollected_Selection_Test_Success() {
        gridViewModel.reinit()
        val currentGridUiState = gridViewModel.uiState.value
        val randomItem = currentGridUiState.items.first()
        gridViewModel.onEvent(UIEvent.OnGridItemSelected(randomItem))
        gridViewModel.onEvent(UIEvent.OnSubmitPressed)
        val subList = currentGridUiState.items.filter {
            it.isSelectedGridItem
        }
        TestCase.assertEquals(subList.size, 1)
        TestCase.assertEquals(randomItem.item, subList.first().item)
    }

    @Test
    fun buildGridCollected_Un_Selection_Test_Success() {
        gridViewModel.reinit()
        val currentGridUiState = gridViewModel.uiState.value
        val randomItem = currentGridUiState.items.first()
        gridViewModel.onEvent(UIEvent.OnGridItemSelected(randomItem))
        gridViewModel.onEvent(UIEvent.OnGridItemSelected(randomItem))
        gridViewModel.onEvent(UIEvent.OnSubmitPressed)
        val subList = currentGridUiState.items.filter {
            it.isSelectedGridItem
        }
        TestCase.assertEquals(subList.size, 0)
    }

    @Test
    fun buildGridMoreOptionTest_Success() {
        gridViewModel.reinit()
        var currentGridUiState = gridViewModel.uiState.value
        gridViewModel.onEvent(UIEvent.OnMoreIconPressed)
        val subList = currentGridUiState.items.filter {
            it.isVisible
        }
        TestCase.assertSame(subList.size, data.size)
    }

    @Test
    fun buildGridMoreOptionDoubleTap_Test_Success() {
        gridViewModel.reinit()
        var currentGridUiState = gridViewModel.uiState.value
        gridViewModel.onEvent(UIEvent.OnMoreIconPressed)
        gridViewModel.onEvent(UIEvent.OnMoreIconPressed)
        val subList = currentGridUiState.items.filter {
            it.isVisible
        }
        TestCase.assertSame(subList.size, limit)
    }
}
