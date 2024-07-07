package com.dee.todoapp.ui.todolist.components

import android.template.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dee.todoapp.ui.theme.LocalAppColor

/**
 * Created by Hein Htet
 */
@Composable
fun AddTodoButton(onCreateTodo: () -> Unit) {
    FloatingActionButton(
        containerColor = LocalAppColor.current.buttonContainerColor,
        onClick = {
            onCreateTodo()
        },
        modifier = Modifier
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(id = R.string.content_description_add_todo)
        )
    }
}