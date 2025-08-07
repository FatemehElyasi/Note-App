package ir.fatemelyasi.note.view.screens.addEditScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.fatemelyasi.note.model.local.entity.CrossEntity
import ir.fatemelyasi.note.model.local.entity.NoteEntity
import ir.fatemelyasi.note.model.local.mapper.mapToNoteViewEntity
import ir.fatemelyasi.note.model.noteLocalRepository.NoteLocalRepository
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddEditNoteViewModel(
    private val repository: NoteLocalRepository
) : ViewModel() {

    var state by mutableStateOf(AddEditNoteState())
        private set

    private var currentNoteId: Int? = null

    fun loadNote(noteId: Int) {
        viewModelScope.launch {
            try {
                val notes = repository.getAllNotes().first()
                val labels = repository.getAllLabels().first()
                val crossRefs = repository.getCrossRefs().first()

                val mappedNotes = mapToNoteViewEntity(notes, labels, crossRefs)
                val target = mappedNotes.find { it.id == noteId } ?: return@launch

                currentNoteId = target.id

                state = state.copy(
                    title = target.title,
                    description = target.description ?: "",
                    image = target.image,
                    labels = target.labels,
                    isFavorite = target.isFavorite
                )
            } catch (e: Exception) {
                state = state.copy(error = e.localizedMessage)
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        state = state.copy(title = newTitle)
    }

    fun onDescriptionChange(newDesc: String) {
        state = state.copy(description = newDesc)
    }

    fun onImageChange(newImage: String?) {
        state = state.copy(image = newImage)
    }

    fun onLabelToggle(label: LabelViewEntity) {
        val exists = state.labels.any { it.labelId == label.labelId }
        val updatedLabels = if (exists) {
            state.labels.filterNot { it.labelId == label.labelId }
        } else {
            state.labels + label
        }
        state = state.copy(labels = updatedLabels)
    }

    fun onFavoriteToggle() {
        state = state.copy(isFavorite = !state.isFavorite)
    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                val now = System.currentTimeMillis()

                val isEditing = currentNoteId != null

                val note = NoteEntity(
                    noteId = currentNoteId,
                    title = state.title,
                    description = state.description,
                    image = state.image,
                    isFavorite = state.isFavorite,
                    createdAt = if (isEditing) state.createdAt ?: now else now,
                    updatedAt = now
                )

                val finalNoteId = if (isEditing) {
                    repository.updateNote(note)
                    currentNoteId!!
                } else {
                    repository.insertNote(note).toInt() ?: 0
                }


                if (isEditing) {
                    repository.deleteCrossRefsForNote(finalNoteId)
                }

                // درج CrossEntity های جدید
                val crossRefs = state.labels.mapNotNull { label ->
                    val noteId = finalNoteId
                    val labelId = label.labelId
                    if (noteId != null && labelId != null) {
                        CrossEntity(noteId = noteId, labelId = labelId)
                    } else null
                }
                repository.insertCrossRefs(crossRefs)

                state = state.copy(isSaved = true)

            } catch (e: Exception) {
                state = state.copy(error = e.localizedMessage)
            }
        }
    }
}
