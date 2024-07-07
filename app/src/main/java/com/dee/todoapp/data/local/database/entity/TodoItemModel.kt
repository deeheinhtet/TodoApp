package com.dee.todoapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Created by Hein Htet
 */

@Entity
data class TodoItemModel(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,
    @ColumnInfo(name = "created_date")
    var createdDate: Date = Date(System.currentTimeMillis()),
    @ColumnInfo(name = "updated_date")
    var updatedDate: Date = Date(System.currentTimeMillis()),
)