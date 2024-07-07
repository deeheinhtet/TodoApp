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

package com.dee.todoapp.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColorsPalette(
    val background: Color = Color.Unspecified,
    val contentBackground: Color = Color.Unspecified,
    val textSurfaceColor: Color = Color.Unspecified,
    val textSurfaceDim: Color = Color.Unspecified,
    val checkboxColor: Color = Color.Unspecified,
    val buttonContainerColor: Color = Color.Unspecified,
)

val LightAppColor = AppColorsPalette(
    background = Color(color = 0xFFFFFFFF),
    contentBackground = Color(color = 0xFFDFDFDF),
    textSurfaceColor = Color(color = 0xFF0E0D0D),
    textSurfaceDim = Color(color = 0xFF444444),
    checkboxColor = Color(color = 0xFF4CAF50),
    buttonContainerColor = Color(color = 0xFF4CAF50),
)

val DarkAppColor = AppColorsPalette(
    background = Color(color = 0xFF000000),
    contentBackground = Color(color = 0xFF252525),
    textSurfaceColor = Color(color = 0xFFFFFFFF),
    textSurfaceDim = Color(color = 0xFFD8D8D8),
    checkboxColor = Color(color = 0xFF055E09),
    buttonContainerColor = Color(color = 0xFF4CAF50),
    )
val LocalAppColor = staticCompositionLocalOf { AppColorsPalette() }
