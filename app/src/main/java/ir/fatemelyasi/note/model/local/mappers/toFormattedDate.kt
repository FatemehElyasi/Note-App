package ir.fatemelyasi.note.model.local.mappers

import java.text.SimpleDateFormat
import java.util.*

fun Long.toFormattedDate(pattern: String = "yyyy/MM/dd HH:mm"): String {
    if (this == 0L) return ""
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    val date = Date(this)
    return sdf.format(date)
}
