package ir.fatemelyasi.note.view.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    internal const val DEFAULT_PATTERN = "yyyy/MM/dd HH:mm"

    private val formatter by lazy {
        SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault())
    }

    fun format(timestamp: Long, pattern: String = DEFAULT_PATTERN): String {
        if (timestamp == 0L) return ""
        return if (pattern == DEFAULT_PATTERN) {
            formatter.format(Date(timestamp))
        } else {
            SimpleDateFormat(pattern, Locale.getDefault()).format(Date(timestamp))
        }
    }
}

fun Long.toFormattedDate(pattern: String = DateFormatter.DEFAULT_PATTERN): String {
    return DateFormatter.format(this, pattern)
}

