package com.rafay.notes.ktx

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.shortToast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
}
