package com.dee.todoapp.utils

import com.google.gson.GsonBuilder

/**
 * Created by Hein Htet
 */

fun <T> toJson(value: T): String? {
    val gson = GsonBuilder().create()
    val json = gson.toJson(value)
    return json
}

inline fun <reified T> fromJson(value: String): T? {
    val gson = GsonBuilder().create()
    return gson.fromJson(value, T::class.java)
}