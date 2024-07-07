package com.dee.todoapp.ui.todolist

import com.dee.todoapp.domain.model.TodoItemDisplayModel

/**
 * Created by Hein Htet
 */
sealed class TodoListEvent{
    data object OnTodoDeleted : TodoListEvent()
    data class OnTodoMarkCompleted(val isCompleted : Boolean) : TodoListEvent()
    data class OnOpenOptions(val todoItemDisplayModel: TodoItemDisplayModel) : TodoListEvent()
}