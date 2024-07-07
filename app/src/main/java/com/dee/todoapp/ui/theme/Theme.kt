import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.dee.todoapp.ui.theme.DarkAppColor
import com.dee.todoapp.ui.theme.LightAppColor
import com.dee.todoapp.ui.theme.LocalAppColor
import com.dee.todoapp.ui.theme.Typography


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val customColorsPalette =
        if (darkTheme) DarkAppColor
        else LightAppColor
    CompositionLocalProvider(
        LocalAppColor provides customColorsPalette
    ) {
        MaterialTheme(
            typography = Typography,
        ) {
            content()
        }
    }
}


