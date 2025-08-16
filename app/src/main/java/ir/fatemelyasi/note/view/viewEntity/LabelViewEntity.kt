package ir.fatemelyasi.note.view.viewEntity

import kotlinx.serialization.Serializable

@Serializable
data class LabelViewEntity(
    val labelId: Long?,
    val labelName: String?,
    val labelColor: String?
)
