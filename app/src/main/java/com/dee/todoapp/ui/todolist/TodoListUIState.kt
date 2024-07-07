package com.dee.todoapp.ui.todolist

import com.dee.todoapp.domain.model.TodoItemDisplayModel
import com.dee.todoapp.ui.base.BaseUIState

/**
 * Created by Hein Htet
 */
sealed class TodoListUIState : BaseUIState() {
    data class Success(val data: List<TodoItemDisplayModel>) : BaseUIState()
}