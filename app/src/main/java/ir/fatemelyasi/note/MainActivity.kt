package ir.fatemelyasi.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ir.fatemelyasi.note.ui.theme.NoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteTheme {
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoteTheme {
    }
}