package com.jgarin.githubtop.ui.list_base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * An abstraction to extract view inflation and ViewHolder creation from the RecyclerView.Adapter.
 * @param listener callback for item clicks
 * @param T the type of items that this presenter can be used with
 * @param VH the type of ViewHolder this Presenter creates
 */
abstract class Presenter<T, VH : ClickableViewHolder<T>>(val listener: ItemClickListener<T, in VH>?) {

	abstract fun createViewHolder(parent: ViewGroup): VH

	protected fun inflate(@LayoutRes layout: Int, parent: ViewGroup): View {
		return LayoutInflater.from(parent.context).inflate(layout, parent, false)
	}

	/**
	 * Item click callback interface
	 */
	interface ItemClickListener<T, VH> {
		fun onItemClicked(viewHolder: VH, item: T, view: View)
	}

}