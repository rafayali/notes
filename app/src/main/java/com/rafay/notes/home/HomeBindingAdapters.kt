package com.rafay.notes.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.rafay.notes.R

@BindingAdapter("done")
fun setDone(imageView: ImageView, done: Boolean) {
    if (done) {
        imageView.setImageDrawable(
            imageView.context.getDrawable(R.drawable.ic_check_circle_black_24dp)
        )
    } else {
        imageView.setImageDrawable(
            imageView.context.getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp)
        )
    }
}
