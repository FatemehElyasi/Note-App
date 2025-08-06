package ir.fatemelyasi.note.view.utils

fun Long.toFormattedDate(): String {
    val formatter = java.text.SimpleDateFormat("yyyy/MM/dd", java.util.Locale.getDefault())
    return formatter.format(java.util.Date(this))
}
