package com.dee.todoapp.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dee.todoapp.ui.theme.LocalAppColor

/**
 * Created by Hein Htet
 */


@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    uiState: BaseUIState? = null,
    errorContent: @Composable (() -> Unit?)? = null,
    content: @Composable () -> Unit,
) {
    println("UI_STATE $uiState")
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LocalAppColor.current.background)
    ) {
        content()
        LoadingView(uiState is UIState.Loading)
        ErrorView(
            isError = uiState is UIState.Error,
            errorContent = errorContent,
            errorMessage = if (uiState is UIState.Error) (uiState.throwable.localizedMessage) else null
        )
    }
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorContent: @Composable (() -> Unit?)? = null, errorMessage: String? = null,
) {
    if (isError) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (errorContent != null) {
                errorContent()
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = errorMessage.orEmpty())
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun LoadingView(showLoadingView: Boolean) {
    if (showLoadingView) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewErrorView() {
    ErrorView(isError = true, errorMessage = "Test Error")
}