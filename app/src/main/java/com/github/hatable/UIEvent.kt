package com.github.hatable

sealed class UIEvent {
    data class OnGridItemSelected(val item: GridProps) : UIEvent()
    object OnSubmitPressed : UIEvent()
    object OnMoreIconPressed : UIEvent()

}