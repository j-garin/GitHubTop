package com.jgarin.githubtop.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jgarin.githubtop.ui.list_base.ClickableViewHolder
import com.jgarin.githubtop.R
import com.jgarin.githubtop.model.Contributor
import com.jgarin.githubtop.ui.list_base.Presenter

class ContributorPresenter(listener: ItemClickListener<Contributor, ViewHolder>) :
		Presenter<Contributor, ContributorPresenter.ViewHolder>(listener) {

	override fun createViewHolder(parent: ViewGroup): ViewHolder {
		return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_contributor, parent, false))
	}

	class ViewHolder(itemView: View) : ClickableViewHolder<Contributor>(itemView) {

		private val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
		private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
		private val tvSubtitle: TextView = itemView.findViewById(R.id.tvSubtitle)

		override fun onBind(item: Contributor) {

			tvTitle.text = item.name
			tvSubtitle.text = tvSubtitle.resources.getString(R.string.xx_contributions, item.contributions)

			Glide.with(ivAvatar)
					.load(item.avatarUrl)
					.into(ivAvatar)

		}
	}

}