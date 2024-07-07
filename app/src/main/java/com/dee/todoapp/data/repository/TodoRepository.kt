package com.dee.todoapp.data.repository

import com.dee.todoapp.data.local.database.dao.TodoDao
import com.dee.todoapp.data.local.database.entity.TodoItemModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Hein Htet
 */
interface TodoRepository {
    val todoItems: Flow<List<TodoItemModel>>
    suspend fun insertTodoItem(todoItemModel: TodoItemModel)
    suspend fun deleteTodoItem(todoItemModel: TodoItemModel)
    suspend fun updateTodoItem(todoItemModel: TodoItemModel)
    suspend fun deleteAllTodoItems()
}


class DefaultTodoRepository @Inject
constructor(private val todoDao: TodoDao) : TodoRepository {
    override val todoItems: Flow<List<TodoItemModel>>
        get() = todoDao.getTodos()

    override suspend fun insertTodoItem(todoItemModel: TodoItemModel) =
        todoDao.insertTodoItem(todoItemModel.copy(createdDate = Date(System.currentTimeMillis())))

    override suspend fun deleteTodoItem(todoItemModel: TodoItemModel) =
        todoDao.deleteTodoItem(todoItemModel)

    override suspend fun updateTodoItem(todoItemModel: TodoItemModel) =
        todoDao.updateTodoItem(todoItemModel.copy(updatedDate = Date(System.currentTimeMillis())))

    override suspend fun deleteAllTodoItems() = todoDao.deleteAllTodoItem()

}

class FakeTodoRepository : TodoRepository{
    override val todoItems: Flow<List<TodoItemModel>>
        get() = flowOf()

    override suspend fun insertTodoItem(todoItemModel: TodoItemModel) {
    }

    override suspend fun deleteTodoItem(todoItemModel: TodoItemModel) {
    }

    override suspend fun updateTodoItem(todoItemModel: TodoItemModel) {
    }

    override suspend fun deleteAllTodoItems() {
    }
}