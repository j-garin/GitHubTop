package com.jgarin.githubtop.ui.list_base

interface PresenterSelector<T, VH: ClickableViewHolder<T>> {

	fun getPresenter(viewType: Int): Presenter<T, VH>

	fun getViewType(item: T) : Int

}