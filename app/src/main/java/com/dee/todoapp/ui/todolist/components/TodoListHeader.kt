package com.dee.todoapp.ui.todolist.components

import android.template.R
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dee.todoapp.ui.theme.LocalAppColor

/**
 * Created by Hein Htet
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListHeader(scrollBehavior: TopAppBarScrollBehavior, count: Int) {
    LargeTopAppBar(
        title = {
            Column {
                Text(
                    text = stringResource(id = R.string.label_your_task),
                    style = MaterialTheme.typography.headlineLarge
                        .copy(color = LocalAppColor.current.textSurfaceColor)
                )
                if (count > 0) {
                    Text(
                        text = stringResource(
                            id = R.string.label_unfinished_task_description,
                            count.toString()
                        ),
                        style = MaterialTheme.typography.labelSmall.copy(color = LocalAppColor.current.textSurfaceDim)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = LocalAppColor.current.background,
            scrolledContainerColor = LocalAppColor.current.background,
        )
    )
}