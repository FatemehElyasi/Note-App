package ir.fatemelyasi.note.viewEntity

import kotlinx.serialization.Serializable

@Serializable
data class LabelViewEntity(
    val labelId: Int? = 0,
    val labelName: String?,
    val labelColor: String?
)
