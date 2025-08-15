package ir.fatemelyasi.note.view.utils

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MessageSnackBarHost(
    hostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = hostState
    ) { data: SnackbarData ->
        Snackbar {
            Text(text = data.visuals.message)
        }
    }
}
