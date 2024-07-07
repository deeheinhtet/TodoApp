package com.dee.todoapp.ui.todolist

import androidx.lifecycle.viewModelScope
import com.dee.todoapp.data.repository.TodoRepository
import com.dee.todoapp.domain.mapper.TodoItemDisplayMapper
import com.dee.todoapp.domain.model.TodoItemDisplayModel
import com.dee.todoapp.ui.base.BaseUIState
import com.dee.todoapp.ui.base.BaseViewModel
import com.dee.todoapp.ui.base.UIState
import com.dee.todoapp.ui.todolist.TodoListUIState.Success
import com.dee.todoapp.ui.todolist.components.TodoItemEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Created by Hein Htet
 */
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val todoItemDisplayMapper: TodoItemDisplayMapper,
) : BaseViewModel() {

    private val _todoSideEffectChannel = Channel<TodoListEvent>(
        capacity = Channel.BUFFERED
    )
    val todoSideEffectChannel: Flow<TodoListEvent>
        get() = _todoSideEffectChannel.receiveAsFlow()

    private val _unFinishedTodoCount = MutableStateFlow(-1)

    val unFinishedTodoCount: StateFlow<Int>
        get() = _unFinishedTodoCount

    private var _toModifyTodoItem: TodoItemDisplayModel? = null

    val toModifyTodoItem: TodoItemDisplayModel?
        get() = _toModifyTodoItem


    val uiState: StateFlow<BaseUIState> = todoRepository
        .todoItems.map {
            _unFinishedTodoCount.value = it.count { !it.isCompleted }
            if (it.isEmpty()) UIState.EmptyData else Success(todoItemDisplayMapper.toUI(it))
        }
        .catch { emit(UIState.Error(it)) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UIState.Loading
        )

    fun updateToModifyTodoItem(itemModel: TodoItemDisplayModel?) {
        _toModifyTodoItem = itemModel
    }

    fun handleTodoItemEvent(
        event: TodoItemEvent,
    ) {
        when (event) {
            is TodoItemEvent.ToggleComplete -> {
                viewModelScope.launch {
                    todoRepository.updateTodoItem(todoItemDisplayMapper.toDefaultItem(event.todoItemModel))
                    _todoSideEffectChannel.send(TodoListEvent.OnTodoMarkCompleted(event.todoItemModel.isCompleted))
                }
            }

            is TodoItemEvent.DeleteTodo -> {
                viewModelScope.launch {
                    todoRepository.deleteTodoItem(todoItemDisplayMapper.toDefaultItem(event.todoItemModel))
                    _todoSideEffectChannel.send(TodoListEvent.OnTodoDeleted)
                }
            }

            is TodoItemEvent.OpenTodoOptions -> {
                viewModelScope.launch {
                    updateToModifyTodoItem(event.todoItemModel)
                    _todoSideEffectChannel.send(TodoListEvent.OnOpenOptions(event.todoItemModel))
                }
            }
        }
    }
}
