package ir.fatemelyasi.note.view.screens.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import ir.fatemelyasi.note.view.screens.navigation.Navigation
import ir.fatemelyasi.note.view.ui.theme.NoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            NoteTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Navigation()
                }
            }
        }
    }
}
