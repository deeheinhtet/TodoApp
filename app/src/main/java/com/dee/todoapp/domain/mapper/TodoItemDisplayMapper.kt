package com.dee.todoapp.domain.mapper

import com.dee.todoapp.data.local.database.entity.TodoItemModel
import com.dee.todoapp.domain.base.BaseMapper
import com.dee.todoapp.domain.model.TodoItemDisplayModel
import com.dee.todoapp.utils.toDisplayDate
import javax.inject.Inject

/**
 * Created by Hein Htet
 */

class TodoItemDisplayMapper @Inject constructor() :
    BaseMapper<List<TodoItemModel>, List<TodoItemDisplayModel>>() {
    override fun toUI(data: List<TodoItemModel>): List<TodoItemDisplayModel> {
        return data.map {
            TodoItemDisplayModel(
                id = it.uid,
                title = it.title,
                description = it.description,
                isCompleted = it.isCompleted,
                updatedDate = it.updatedDate.toDisplayDate()
            )
        }
    }

    override fun toDefault(data: List<TodoItemDisplayModel>): List<TodoItemModel> {
        return data.map {
            TodoItemModel(
                uid = it.id,
                title = it.title,
                description = it.description,
                isCompleted = it.isCompleted,
            )
        }
    }

    fun toDefaultItem(itemDisplayModel: TodoItemDisplayModel) = TodoItemModel(
        uid = itemDisplayModel.id,
        title = itemDisplayModel.title,
        description = itemDisplayModel.description,
        isCompleted = itemDisplayModel.isCompleted,
    )
}