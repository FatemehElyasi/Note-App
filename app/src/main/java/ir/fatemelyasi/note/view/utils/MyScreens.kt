package ir.fatemelyasi.note.view.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class MyScreens() {

    @Serializable
    object NoteListScreen : MyScreens()

    @Serializable
    data class NoteDetailScreen(
        val noteId: Int,
    ) : MyScreens()

    @Serializable
    data class AddEditNoteScreen(
        val noteId: Int? = null,
    ) : MyScreens()

}
