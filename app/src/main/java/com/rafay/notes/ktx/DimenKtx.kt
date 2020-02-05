package com.rafay.notes.ktx

import android.content.Context
import android.util.TypedValue

/**
 * Converts pixel into dp using [TypedValue].
 */
fun Int.toDp(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}
