package com.dee.todoapp.ui.todolist.components

import AppTheme
import android.content.res.Configuration
import android.template.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dee.todoapp.domain.model.TodoItemDisplayModel
import com.dee.todoapp.ui.theme.LocalAppColor

/**
 * Created by Hein Htet
 */


@Composable
fun TodoItemView(
    modifier: Modifier,
    todoItemModel: TodoItemDisplayModel,
    event: (TodoItemEvent) -> Unit = {},
) {
    val isCompletedTask = todoItemModel.isCompleted
    Box(modifier = modifier) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            shape = RoundedCornerShape(4),
            colors = CardDefaults.cardColors().copy(
                containerColor = if (isCompletedTask) LocalAppColor.current.contentBackground.copy(
                    alpha = 0.5f
                ) else LocalAppColor.current.contentBackground
            )
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    modifier = Modifier.absoluteOffset((0).dp, 0.dp),
                    colors = CheckboxDefaults.colors()
                        .copy(
                            checkedCheckmarkColor = LocalAppColor.current.textSurfaceColor,
                            checkedBorderColor = LocalAppColor.current.textSurfaceDim,
                            uncheckedBorderColor = LocalAppColor.current.textSurfaceDim,
                            checkedBoxColor = LocalAppColor.current.checkboxColor,
                        ),
                    checked = todoItemModel.isCompleted, onCheckedChange = {
                        event.invoke(TodoItemEvent.ToggleComplete(todoItemModel.copy(isCompleted = it)))
                    })
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 4.dp,
                            bottom = 16.dp,
                            top = 8.dp
                        ),
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .weight(1f),
                            text = todoItemModel.title,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = LocalAppColor.current.textSurfaceColor,
                                textDecoration = if (isCompletedTask) TextDecoration.LineThrough else TextDecoration.None
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(
                                id = R.string.content_description_modify_todo
                            ),
                            tint = LocalAppColor.current.textSurfaceColor,
                            modifier = Modifier
                                .padding(top = 4.dp, end = 8.dp)
                                .clickable {
                                    event.invoke(
                                        TodoItemEvent.OpenTodoOptions(
                                            todoItemModel
                                        )
                                    )
                                }
                        )
                    }
                    Text(
                        text = todoItemModel.description,
                        modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = LocalAppColor.current.textSurfaceColor,
                            textDecoration = if (isCompletedTask) TextDecoration.LineThrough else TextDecoration.None
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp, end = 8.dp)
                            .align(
                                Alignment.End,
                            ),
                        text = todoItemModel.updatedDate,
                        style = MaterialTheme.typography.labelSmall.copy(color = LocalAppColor.current.textSurfaceDim)
                    )
                }
            }
        }
    }
}

sealed class TodoItemEvent {
    data class ToggleComplete(val todoItemModel: TodoItemDisplayModel) : TodoItemEvent()
    data class DeleteTodo(val todoItemModel: TodoItemDisplayModel) : TodoItemEvent()
    data class OpenTodoOptions(val todoItemModel: TodoItemDisplayModel) : TodoItemEvent()
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTodoItemView() {
    AppTheme {
        TodoItemView(
            modifier = Modifier,
            todoItemModel = TodoItemDisplayModel(
                title = "Todo",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                isCompleted = true,
                updatedDate = "July 6, 2023",
                id = 1
            )
        )
    }
}