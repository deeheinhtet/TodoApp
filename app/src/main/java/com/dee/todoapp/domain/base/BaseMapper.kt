package com.dee.todoapp.domain.base

/**
 * Created by Hein Htet
 */
abstract class BaseMapper<I, O> {
    abstract fun toUI(data : I): O
    abstract fun  toDefault(data : O): I
}