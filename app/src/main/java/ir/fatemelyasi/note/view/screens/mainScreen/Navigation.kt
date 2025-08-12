package ir.fatemelyasi.note.view.screens.mainScreen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ir.fatemelyasi.note.view.screens.addEditScreen.AddEditNoteScreen
import ir.fatemelyasi.note.view.screens.addEditScreen.AddEditNoteViewModel
import ir.fatemelyasi.note.view.utils.MyScreens
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MyScreens.AddEditNoteScreen::class
    ) {
        composable<MyScreens.AddEditNoteScreen> { backStackEntry ->
            val dataModel = backStackEntry.toRoute<MyScreens.AddEditNoteScreen>()
            val viewModel: AddEditNoteViewModel = koinViewModel()

            AddEditNoteScreen(
                noteId = dataModel.noteId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
