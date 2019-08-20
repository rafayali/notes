package com.rafay.notes.util

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView item decoration which adds space between items.
 *
 * @author Rafay Ali
 */
class VerticalSpaceItemDecoration(
    private val bottomInPx: Float,
    private val leftInPx: Float,
    private val topInPx: Float,
    private val rightInPx: Float,
    private val lastItemSpaceInPx: Float? = null
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val bottom = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            bottomInPx,
            parent.context?.resources?.displayMetrics
        ).toInt()
        val top = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            topInPx,
            parent.context?.resources?.displayMetrics
        ).toInt()

        val start = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            leftInPx,
            parent.context?.resources?.displayMetrics
        ).toInt()

        val end = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            rightInPx,
            parent.context?.resources?.displayMetrics
        ).toInt()

        val lastItemSpace = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            lastItemSpaceInPx ?: bottomInPx,
            parent.context?.resources?.displayMetrics
        ).toInt()

        val position = parent.getChildAdapterPosition(view)
        val last = state.itemCount - 1
        val first = 0

        when (position) {
            first -> {
                outRect.top = top
                outRect.left = start
                outRect.right = end
                outRect.bottom = bottom
            }
            last -> {
                outRect.bottom = lastItemSpace
                outRect.left = start
                outRect.right = end
                outRect.top = 0
            }
            else -> {
                outRect.top = 0
                outRect.left = start
                outRect.right = end
                outRect.bottom = bottom
            }
        }
    }
}