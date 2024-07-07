package com.dee.todoapp.ui.modifytodo

/**
 * Created by Hein Htet
 */
sealed class ModifyTodoEvent {
    data object TodoCreated : ModifyTodoEvent()
    data object TodoModified : ModifyTodoEvent()
}