package com.dee.todoapp.ui.modifytodo

import android.template.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.dee.todoapp.Constants.CREATED
import com.dee.todoapp.Constants.ROUTE_BACK_MODIFY_TODO_KEY
import com.dee.todoapp.Constants.UPDATED
import com.dee.todoapp.domain.model.TodoItemDisplayModel
import com.dee.todoapp.ui.LocalNavController
import com.dee.todoapp.ui.base.BaseScreen
import com.dee.todoapp.ui.modifytodo.components.TodoInputField
import com.dee.todoapp.ui.theme.LocalAppColor

/**
 * Created by Hein Htet
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyTodoScreen(
    viewModel: ModifyTodoViewModel = hiltViewModel(),
    todoItem: TodoItemDisplayModel?,
) {
    val navigation = LocalNavController.current
    val lifecycleOwner = LocalLifecycleOwner.current


    var titleTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    var descriptionTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }

    val isValidate =
        viewModel.isValidateToSaveTodo.collectAsStateWithLifecycle(initialValue = false)


    LaunchedEffect(viewModel.todoSideEffectChannel) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.todoSideEffectChannel.collect { effect ->
                when (effect) {
                    is ModifyTodoEvent.TodoModified -> {
                        navigation.popBackStack()
                        navigation.currentBackStackEntry?.savedStateHandle?.set(
                            ROUTE_BACK_MODIFY_TODO_KEY,
                            UPDATED
                        )
                    }

                    is ModifyTodoEvent.TodoCreated -> {
                        navigation.popBackStack()
                        navigation.currentBackStackEntry?.savedStateHandle?.set(
                            ROUTE_BACK_MODIFY_TODO_KEY,
                            CREATED
                        )
                    }
                }
            }
        }
    }

    DisposableEffect(key1 = Unit) {
        if (todoItem != null) {
            viewModel.todoItemDisplayModel = todoItem
            titleTextFieldValue = titleTextFieldValue.copy(text = todoItem.title)
            descriptionTextFieldValue = titleTextFieldValue.copy(text = todoItem.description)
            viewModel.initTodoDisplayItem(todoItem)
        }
        onDispose { }
    }

    BaseScreen {
        Scaffold(
            containerColor = LocalAppColor.current.background,
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = if (!viewModel.isEditMode) R.string.label_add_new_todo else R.string.label_edit_todo),
                                style = MaterialTheme.typography.headlineLarge
                                    .copy(color = LocalAppColor.current.textSurfaceColor)
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navigation.popBackStack()
                            }) {
                                Icon(
                                    tint = LocalAppColor.current.textSurfaceColor,
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = ""
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors().copy(
                            containerColor = LocalAppColor.current.background,
                            scrolledContainerColor = LocalAppColor.current.background,
                        )
                    )
                    TodoInputField(
                        title = stringResource(id = R.string.hint_label_input_todo_title),
                        textFieldValue = titleTextFieldValue,
                        placeholderText = stringResource(
                            id = R.string.hint_label_input_todo_title
                        ),
                        onTextChanged = {
                            titleTextFieldValue = it
                            viewModel.updateTodoTitle(it.text)
                        })
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    TodoInputField(
                        modifier = Modifier.weight(1f),
                        title = stringResource(id = R.string.hint_label_input_todo_description),
                        textFieldValue = descriptionTextFieldValue,
                        placeholderText = stringResource(
                            id = R.string.hint_label_input_todo_description
                        ),
                        onTextChanged = {
                            descriptionTextFieldValue = it
                            viewModel.updateTodoDescription(it.text)
                        })
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = LocalAppColor.current.buttonContainerColor,
                            disabledContainerColor = LocalAppColor.current.contentBackground
                        ),
                        enabled = isValidate.value,
                        shape = RoundedCornerShape(8),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(bottom = 16.dp),
                        onClick = {
                            viewModel.saveTodo()
                        }) {
                        Text(
                            text = stringResource(id = R.string.action_label_save),
                            style = MaterialTheme.typography.bodyMedium.copy(color = LocalAppColor.current.textSurfaceColor)
                        )
                    }
                }
            }
        }
    }
}