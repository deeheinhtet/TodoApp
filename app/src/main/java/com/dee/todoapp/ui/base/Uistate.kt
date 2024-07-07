package com.dee.todoapp.ui.base

/**
 * Created by Hein Htet
 */


abstract class BaseUIState


sealed class UIState : BaseUIState() {
    data object Loading : UIState()
    data object EmptyData : UIState()
    data class Error(val throwable: Throwable) : UIState()
}