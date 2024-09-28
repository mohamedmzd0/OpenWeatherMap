package com.mohamed.utils.date

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(
    dtTxt: String,
    fromPattern: String = "yyyy-MM-dd HH:mm:ss",
    toPattern: String = "EEEE, MMMM d, yyyy"
): String {
    val inputFormat = SimpleDateFormat(fromPattern, Locale.US)
    val outputFormat = SimpleDateFormat(toPattern, Locale.getDefault())
    val date = inputFormat.parse(dtTxt) ?: return ""
    return outputFormat.format(date)
}