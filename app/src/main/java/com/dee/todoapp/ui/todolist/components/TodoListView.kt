package com.dee.todoapp.ui.todolist.components

import android.template.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dee.todoapp.domain.model.TodoItemDisplayModel
import com.dee.todoapp.ui.base.ErrorView
import com.dee.todoapp.ui.theme.LocalAppColor

/**
 * Created by Hein Htet
 */
@Composable
fun TodoListView(
    modifier: Modifier = Modifier,
    items: List<TodoItemDisplayModel>? = emptyList(),
    todoItemEvent: (TodoItemEvent) -> Unit = {},
) {
    if (items.isNullOrEmpty()) {
        ErrorView(isError = true,
            modifier = modifier,
            errorContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LocalAppColor.current.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = stringResource(id = R.string.label_empty_todo),
                        tint = LocalAppColor.current.textSurfaceDim,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.label_empty_todo),
                        style = MaterialTheme.typography.bodyLarge.copy(color = LocalAppColor.current.textSurfaceDim)
                    )
                }
            })
    } else {
        LazyColumn(modifier = modifier) {
            items(items) { todo ->
                TodoItemView(modifier = Modifier, todoItemModel = todo, event = todoItemEvent)
            }
        }
    }
}