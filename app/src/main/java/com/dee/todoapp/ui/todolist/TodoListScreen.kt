package com.dee.todoapp.ui.todolist

import AppTheme
import android.content.res.Configuration
import android.template.R
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.dee.todoapp.Constants
import com.dee.todoapp.Constants.ROUTE_BACK_MODIFY_TODO_KEY
import com.dee.todoapp.data.repository.FakeTodoRepository
import com.dee.todoapp.domain.mapper.TodoItemDisplayMapper
import com.dee.todoapp.ui.LocalNavController
import com.dee.todoapp.ui.ROUTE_ADD_TODO
import com.dee.todoapp.ui.base.BaseScreen
import com.dee.todoapp.ui.theme.LocalAppColor
import com.dee.todoapp.ui.todolist.components.AddTodoButton
import com.dee.todoapp.ui.todolist.components.TodoItemEvent
import com.dee.todoapp.ui.todolist.components.TodoListHeader
import com.dee.todoapp.ui.todolist.components.TodoListView
import com.dee.todoapp.utils.toJson
import kotlinx.coroutines.launch

/**
 * Created by Hein Htet
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(viewModel: TodoListViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val unFinishedCount = viewModel.unFinishedTodoCount.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    var showOptionBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    if (showOptionBottomSheet) {
        TodoListOptionBottomSheet(onEdit = {
            navController.navigate(ROUTE_ADD_TODO.plus("/todo?=${toJson(viewModel.toModifyTodoItem)}"))
            viewModel.updateToModifyTodoItem(null)
        }, onDelete = {
            viewModel.toModifyTodoItem?.let {
                viewModel.handleTodoItemEvent(
                    TodoItemEvent.DeleteTodo(
                        it
                    )
                )
            }
            viewModel.updateToModifyTodoItem(null)
        }, onDismiss = {
            showOptionBottomSheet = false
        })
    }


    LaunchedEffect(viewModel.todoSideEffectChannel) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.todoSideEffectChannel.collect { effect ->
                snackbarHostState.currentSnackbarData?.dismiss()
                when (effect) {
                    is TodoListEvent.OnTodoDeleted ->
                        scope.launch { snackbarHostState.showSnackbar(context.getString(R.string.label_todo_deleted_success)) }

                    is TodoListEvent.OnTodoMarkCompleted -> {
                        val result = if (effect.isCompleted) context.getString(
                            R.string.label_completed
                        ) else context.getString(R.string.label_uncompleted)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                context.getString(R.string.label_todo_created_mark_action, result)
                            )
                        }
                    }

                    is TodoListEvent.OnOpenOptions -> {
                        showOptionBottomSheet = true
                    }
                }
            }
        }
    }

    navController.currentBackStackEntry?.let {
        val popReturnResult by it.savedStateHandle.getStateFlow(
            Constants.ROUTE_BACK_MODIFY_TODO_KEY,
            ""
        ).collectAsState()

        if (popReturnResult.isNotEmpty()) {
            scope.launch {
                snackbarHostState.showSnackbar(context.getString(if (popReturnResult == Constants.CREATED) R.string.label_todo_created_success else R.string.label_todo_updated_success))
            }
            it.savedStateHandle.remove<String>(ROUTE_BACK_MODIFY_TODO_KEY)
        }
    }

    BaseScreen(uiState = uiState.value, content = {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            floatingActionButton = {
                AddTodoButton {
                    navController.navigate(ROUTE_ADD_TODO.plus("/todo?="))
                }
            },
            containerColor = LocalAppColor.current.background,
            topBar = { TodoListHeader(scrollBehavior, unFinishedCount.value) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) { innerPadding ->
            TodoListView(
                modifier = Modifier.padding(innerPadding),
                items = if (uiState.value is TodoListUIState.Success) (uiState.value as TodoListUIState.Success).data else emptyList(),
                todoItemEvent = {
                    viewModel.handleTodoItemEvent(it)
                }
            )
        }
    })
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TodoListScreenPreview() {
    AppTheme {
        TodoListScreen(
            viewModel = TodoListViewModel(
                FakeTodoRepository(), TodoItemDisplayMapper()
            )
        )
    }
}