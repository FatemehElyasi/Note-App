package ir.fatemelyasi.note.viewEntity

import kotlinx.serialization.Serializable

@Serializable
data class LabelViewEntity(
    val labelId: Long? = null,
    val labelName: String?,
    val labelColor: String?
)
