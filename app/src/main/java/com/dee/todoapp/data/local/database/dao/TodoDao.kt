package com.dee.todoapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dee.todoapp.data.local.database.entity.TodoItemModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Hein Htet
 */

@Dao
interface TodoDao {
    @Query("SELECT * FROM todoitemmodel ORDER BY uid DESC LIMIT 10")
    fun getTodos(): Flow<List<TodoItemModel>>

    @Insert
    suspend fun insertTodoItem(item: TodoItemModel)

    @Delete
    suspend fun deleteTodoItem(item: TodoItemModel)

    @Update
    suspend fun updateTodoItem(item: TodoItemModel)

    @Query("DELETE FROM todoitemmodel")
    suspend fun deleteAllTodoItem()
}
