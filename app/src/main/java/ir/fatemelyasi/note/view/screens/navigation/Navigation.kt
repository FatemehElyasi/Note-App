package ir.fatemelyasi.note.view.screens.navigation

import ir.fatemelyasi.note.view.screens.homeListScreen.NoteListViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ir.fatemelyasi.note.view.screens.addEditScreen.AddEditNoteScreen
import ir.fatemelyasi.note.view.screens.addEditScreen.AddEditNoteViewModel
import ir.fatemelyasi.note.view.screens.detailScreen.NoteDetailScreen
import ir.fatemelyasi.note.view.screens.detailScreen.NoteDetailViewModel
import ir.fatemelyasi.note.view.screens.homeListScreen.HomeNoteListScreen
import ir.fatemelyasi.note.view.utils.myScreens.MyScreens
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MyScreens.NoteListScreen::class
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
        composable<MyScreens.NoteDetailScreen> { backStackEntry ->
            val dataModel = backStackEntry.toRoute<MyScreens.NoteDetailScreen>()
            val viewModel: NoteDetailViewModel = koinViewModel()

            NoteDetailScreen(
                noteId = dataModel.noteId,
                onEditClick = {
                    navController
                        .navigate(
                            MyScreens.AddEditNoteScreen(
                                noteId = dataModel.noteId
                            )
                        )
                },
                onBack = { navController.popBackStack() },
                viewModel = viewModel,
            )
        }
        composable<MyScreens.NoteListScreen> { backStackEntry ->
            val viewModel: NoteListViewModel = koinViewModel()
            HomeNoteListScreen(
                onNoteClicked = { note ->
                    navController.navigate(
                        MyScreens.NoteDetailScreen(noteId = note.id ?: -1)
                    )

                },
                onAddNoteClicked = {
                    navController.navigate(MyScreens.AddEditNoteScreen())
                },
                onSearchClicked = {},
                viewModel = viewModel
            )
        }
    }
}


