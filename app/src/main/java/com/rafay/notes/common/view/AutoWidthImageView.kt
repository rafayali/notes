package com.rafay.notes.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * ImageView which changes its width as much as its height to keep image in square shape.
 */
class AutoWidthImageView(
    context: Context,
    attrs: AttributeSet?,
    defStylesAttr: Int
) : AppCompatImageView(context, attrs, defStylesAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec)
        // Pass measuredHeight for both params to make image square proportional
        setMeasuredDimension(measuredHeight, measuredHeight)
    }
}