package com.jgarin.githubtop.ui.list_base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Extended RecyclerView.ViewHolder class that supports adding click listeners
 */
abstract class ClickableViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {

	abstract fun onBind(item: T)

}