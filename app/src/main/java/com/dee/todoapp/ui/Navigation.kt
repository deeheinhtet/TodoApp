/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dee.todoapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dee.todoapp.domain.model.TodoItemDisplayModel
import com.dee.todoapp.ui.modifytodo.ModifyTodoScreen
import com.dee.todoapp.ui.todolist.TodoListScreen
import com.dee.todoapp.utils.fromJson

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ROUTE_TODO_LIST) {
        composable(ROUTE_TODO_LIST) {
            TodoListScreen()
        }
        composable(
            route = ROUTE_ADD_TODO.plus("/todo?={todoItem}"),
            arguments = listOf(navArgument("todoItem") {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val todo = backStackEntry.arguments?.getString("todoItem")
            var todoItemDisplayModel: TodoItemDisplayModel? = null
            if (todo != null) {
                todoItemDisplayModel = fromJson(todo)
            }
            ModifyTodoScreen(todoItem = todoItemDisplayModel)
        }

    }
}

const val ROUTE_TODO_LIST = "route_todo_list"
const val ROUTE_ADD_TODO = "route_add_todo"