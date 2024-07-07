package com.dee.todoapp.ui.modifytodo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dee.todoapp.ui.theme.LocalAppColor

/**
 * Created by Hein Htet
 */

@Composable
fun TodoInputField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    title: String,
    placeholderText: String,
    onTextChanged: (TextFieldValue) -> Unit = {},
) {
    Column(modifier = modifier) {
        InputTitleLabel(text = title)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .then(modifier),
            value = textFieldValue,
            onValueChange = {
                onTextChanged(it)
            },
            placeholder = { InputPlaceholderLabel(text = placeholderText) },
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(color = LocalAppColor.current.textSurfaceColor),
            colors = TextFieldDefaults.colors().copy(
                focusedPlaceholderColor = LocalAppColor.current.textSurfaceDim,
                disabledPlaceholderColor = LocalAppColor.current.textSurfaceDim,
                errorPlaceholderColor = LocalAppColor.current.textSurfaceDim,
                unfocusedPlaceholderColor = LocalAppColor.current.textSurfaceDim,
                focusedContainerColor = LocalAppColor.current.contentBackground,
                errorContainerColor = LocalAppColor.current.contentBackground,
                disabledContainerColor = LocalAppColor.current.contentBackground,
                unfocusedContainerColor = LocalAppColor.current.contentBackground,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun InputPlaceholderLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(
            color = LocalAppColor.current.textSurfaceDim,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun InputTitleLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            color = LocalAppColor.current.textSurfaceColor,
            fontWeight = FontWeight.SemiBold
        )
    )
}