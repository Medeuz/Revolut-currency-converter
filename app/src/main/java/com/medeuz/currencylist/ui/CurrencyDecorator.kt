package com.medeuz.currencylist.ui

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CurrencyDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            val offset =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, view.resources.displayMetrics).toInt()
            bottom = offset
            top = if (parent.getChildLayoutPosition(view) % state.itemCount == 0) offset else 0
            left = offset
            right = offset
        }
    }
}