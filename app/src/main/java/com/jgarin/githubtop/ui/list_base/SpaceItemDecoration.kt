package com.jgarin.githubtop.ui.list_base

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException


class SpaceItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

	constructor(context: Context, @DimenRes sizeRes: Int) : this(context.resources.getDimensionPixelSize(sizeRes))

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

		val manager = (parent.layoutManager as? LinearLayoutManager)
		              ?: throw IllegalArgumentException("This decoration will only work with LinearLayoutManager")
		val orientation = manager.orientation

		val index = parent.getChildAdapterPosition(view)
		val isLastItem = index == parent.adapter?.itemCount?.let { it - 1 } ?: 0
		val isFirstItem = index == 0
		if (manager.reverseLayout) { // for reverse item order
			when (orientation) {
				RecyclerView.VERTICAL   -> {
					outRect.top = if (isLastItem) margin else 0
					outRect.bottom = margin
				}
				RecyclerView.HORIZONTAL -> {
					outRect.left = margin
					outRect.right = if (isFirstItem) margin else 0
				}
			}
		} else { // for normal item order
			when (orientation) {
				RecyclerView.VERTICAL   -> {
					outRect.top = if (isFirstItem) margin else 0
					outRect.bottom = margin
				}
				RecyclerView.HORIZONTAL -> {
					outRect.right = margin
					outRect.left = if (isFirstItem) margin else 0
				}
			}
		}
	}

}