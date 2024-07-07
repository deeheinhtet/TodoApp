package com.dee.todoapp.ui.todolist

import android.template.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dee.todoapp.ui.theme.LocalAppColor
import kotlinx.coroutines.launch

/**
 * Created by Hein Htet
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListOptionBottomSheet(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val hideModalBottomSheet: () -> Unit =
        { coroutineScope.launch { modalBottomSheetState.hide() } }
    ModalBottomSheet(
        containerColor = LocalAppColor.current.contentBackground,
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onEdit()
                        onDismiss()
                        hideModalBottomSheet()
                    }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = LocalAppColor.current.textSurfaceDim,
                    contentDescription = stringResource(id = R.string.content_description_edit_todo)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.action_label_edit),
                    style = MaterialTheme.typography.bodyMedium.copy(color = LocalAppColor.current.textSurfaceColor)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 12.dp)
                    .background(LocalAppColor.current.textSurfaceDim.copy(alpha = 0.2f))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDelete()
                        onDismiss()
                        hideModalBottomSheet()
                    }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = LocalAppColor.current.textSurfaceDim,
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.content_description_edit_todo)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.action_label_delete),
                    style = MaterialTheme.typography.bodyMedium.copy(color = LocalAppColor.current.textSurfaceColor)
                )
            }
        }
    }
}