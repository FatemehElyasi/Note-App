package ir.fatemelyasi.note.view.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ir.fatemelyasi.note.R
import ir.fatemelyasi.note.view.ui.theme.Blue
import ir.fatemelyasi.note.view.ui.theme.Cyan
import ir.fatemelyasi.note.view.ui.theme.Green
import ir.fatemelyasi.note.view.ui.theme.Purple
import ir.fatemelyasi.note.view.ui.theme.Red
import ir.fatemelyasi.note.view.ui.theme.Teal200
import ir.fatemelyasi.note.view.ui.theme.Yellow
import ir.fatemelyasi.note.view.utils.formatted.toHex
import ir.fatemelyasi.note.view.viewEntity.LabelViewEntity


@Composable
fun labelsMock(): List<LabelViewEntity> {
    return listOf(
        LabelViewEntity(1, stringResource(R.string.label_work), Yellow.toHex()),
        LabelViewEntity(2, stringResource(R.string.label_personal), Teal200.toHex()),
        LabelViewEntity(3, stringResource(R.string.label_ideas), Blue.toHex()),
        LabelViewEntity(4, stringResource(R.string.label_university), Cyan.toHex()),
        LabelViewEntity(5, stringResource(R.string.label_resume), Purple.toHex()),
        LabelViewEntity(6, stringResource(R.string.label_in_process), Green.toHex()),
        LabelViewEntity(6, stringResource(R.string.label_force), Red.toHex()),


    )
}




