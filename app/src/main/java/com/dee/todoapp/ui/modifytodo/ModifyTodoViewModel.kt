package com.dee.todoapp.ui.modifytodo

import androidx.lifecycle.viewModelScope
import com.dee.todoapp.data.local.database.entity.TodoItemModel
import com.dee.todoapp.data.repository.TodoRepository
import com.dee.todoapp.domain.mapper.TodoItemDisplayMapper
import com.dee.todoapp.domain.model.TodoItemDisplayModel
import com.dee.todoapp.ui.base.BaseViewModel
import com.dee.todoapp.ui.todolist.TodoListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Created by Hein Htet
 */
@HiltViewModel
class ModifyTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val mapper: TodoItemDisplayMapper,
) : BaseViewModel() {

    private val _todoTitle = MutableStateFlow("")
    private val _todoDescription = MutableStateFlow("")
    var todoItemDisplayModel: TodoItemDisplayModel? = null


    val todoTitle: StateFlow<String>
        get() = _todoTitle

    val todoDescription: StateFlow<String>
        get() = _todoDescription

    val isEditMode: Boolean
        get() = todoItemDisplayModel != null

    private val _todoSideEffectChannel = Channel<ModifyTodoEvent>(
        capacity = Channel.BUFFERED
    )
    val todoSideEffectChannel: Flow<ModifyTodoEvent>
        get() = _todoSideEffectChannel.receiveAsFlow()


    val isValidateToSaveTodo = combine(todoTitle, todoDescription) { title, description ->
        title.isNotEmpty() && description.isNotEmpty()
    }

    fun updateTodoTitle(value: String) {
        _todoTitle.value = value
    }

    fun updateTodoDescription(value: String) {
        _todoDescription.value = value
    }

    fun initTodoDisplayItem(itemDisplayModel: TodoItemDisplayModel) {
        _todoTitle.value = itemDisplayModel.title
        _todoDescription.value = itemDisplayModel.title
    }

    fun saveTodo() {
        viewModelScope.launch {
            if (todoItemDisplayModel != null) {
                todoItemDisplayModel?.let {
                    repository.updateTodoItem(
                        mapper.toDefaultItem(
                            it.copy(
                                title = todoTitle.value,
                                description = todoDescription.value
                            )
                        )
                    )
                    _todoSideEffectChannel.send(ModifyTodoEvent.TodoModified)
                }
            } else {
                repository.insertTodoItem(
                    TodoItemModel(
                        title = todoTitle.value,
                        description = todoDescription.value,
                        isCompleted = false
                    )
                )
                _todoSideEffectChannel.send(ModifyTodoEvent.TodoCreated)
            }
        }
    }
}