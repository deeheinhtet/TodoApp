package com.dee.todoapp.data.di

import com.dee.todoapp.domain.mapper.TodoItemDisplayMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Hein Htet
 */

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    @Singleton
    fun provideTodoItemDisplayMapper() = TodoItemDisplayMapper()
}