package sillapps.com.data.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String {
    val inFormat = SimpleDateFormat(DATE_SERVER_FORMAT, Locale.getDefault())
    val outFormat = SimpleDateFormat(DATE_DISPLAY_FORMAT, Locale.getDefault())
    return outFormat.format(inFormat.parse(this) ?: EMPTY_STR)
}