package com.jgarin.githubtop.ui.list_base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.supportiv.ui.lists.SinglePresenterSelector

/**
 * Magical adapter that uses DiffUtil under the hood in a background thread.
 * @param selector responsible for view inflation
 * @param callback compares items
 * @param T the type of items that will be in the list
 * @param VH ViewHolder type
 *
 */
class DiffListAdapter<T, VH : ClickableViewHolder<T>>(private val selector: PresenterSelector<T, VH>,
                                                                                        callback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
	                                                      override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

	                                                      override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
                                                      }
) : ListAdapter<T, VH>(callback) {

	constructor(presenter: Presenter<T, VH>): this(SinglePresenterSelector(presenter))

	constructor(presenter: Presenter<T, VH>, callback: DiffUtil.ItemCallback<T>) : this(SinglePresenterSelector(presenter), callback)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
		return selector.getPresenter(viewType).createViewHolder(parent)
	}

	fun getListItem(position: Int): T = super.getItem(position)

	override fun getItemViewType(position: Int): Int {
		return selector.getViewType(getItem(position))
	}

	override fun onBindViewHolder(holder: VH, position: Int) {
		val item = getItem(position)
		holder.itemView.setOnClickListener { selector.getPresenter(getItemViewType(position)).listener?.onItemClicked(holder, item, it) }
		holder.onBind(item)
	}

}