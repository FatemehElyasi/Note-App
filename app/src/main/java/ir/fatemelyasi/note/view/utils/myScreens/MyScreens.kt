package ir.fatemelyasi.note.view.utils.myScreens

import kotlinx.serialization.Serializable

@Serializable
sealed class MyScreens() {

    @Serializable
    object NoteListScreen : MyScreens()

    @Serializable
    data class NoteDetailScreen(
        val noteId: Long,
    ) : MyScreens()

    @Serializable
    data class AddEditNoteScreen(
        val noteId: Long? = null,
    ) : MyScreens()

}