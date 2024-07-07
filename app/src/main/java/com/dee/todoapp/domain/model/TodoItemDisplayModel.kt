package com.dee.todoapp.domain.model

/**
 * Created by Hein Htet
 */

data class TodoItemDisplayModel(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val updatedDate: String,
)