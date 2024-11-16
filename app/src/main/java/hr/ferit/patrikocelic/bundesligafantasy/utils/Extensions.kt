package hr.ferit.patrikocelic.bundesligafantasy.utils

import android.text.format.DateFormat
import android.util.DisplayMetrics
import hr.ferit.patrikocelic.bundesligafantasy.App.Companion.INSTANCE
import java.util.*

val Int.dp: Float
    get() = this * (INSTANCE.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

val Float.dp: Float
    get() = this * (INSTANCE.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun Long.toDate(format: String = "dd.MM.yyyy"): String {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = this
    return DateFormat.format(format, cal).toString()
}

fun Int.toDate(format: String = "dd.MM.yyyy"): String {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = this.toLong()
    return DateFormat.format(format, cal).toString()
}