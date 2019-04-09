package com.supportiv.ui.lists

import com.jgarin.githubtop.ui.list_base.ClickableViewHolder
import com.jgarin.githubtop.ui.list_base.Presenter
import com.jgarin.githubtop.ui.list_base.PresenterSelector

internal class SinglePresenterSelector<T, VH : ClickableViewHolder<T>>(val presenter: Presenter<T, VH>) :
		PresenterSelector<T, VH> {

	override fun getPresenter(viewType: Int): Presenter<T, VH> = presenter

	override fun getViewType(item: T): Int = 0

}