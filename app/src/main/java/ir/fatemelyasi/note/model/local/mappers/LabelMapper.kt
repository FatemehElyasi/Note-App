package ir.fatemelyasi.note.model.local.mappers

import ir.fatemelyasi.note.model.local.entity.LabelEntity
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity

//LabelEntity -> LabelViewEntity = DB -> UI
fun LabelEntity.toViewEntity(): LabelViewEntity {
    return LabelViewEntity(
        labelId = this.labelId,
        labelName = this.labelName,
        labelColor = this.labelColor
    )
}
//LabelViewEntity -> LabelEntity = UI -> DB
fun LabelViewEntity.toEntity(): LabelEntity {
    return LabelEntity(
        labelId = this.labelId,
        labelName = this.labelName,
        labelColor = this.labelColor
    )
}
