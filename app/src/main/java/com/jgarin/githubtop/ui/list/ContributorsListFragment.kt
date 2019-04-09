package com.jgarin.githubtop.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgarin.githubtop.R
import com.jgarin.githubtop.extensions.observeNotNull
import com.jgarin.githubtop.extensions.snackbar
import com.jgarin.githubtop.model.Contributor
import com.jgarin.githubtop.ui.ContributorsListApplication
import com.jgarin.githubtop.ui.list_base.DiffListAdapter
import com.jgarin.githubtop.ui.Navigator
import com.jgarin.githubtop.ui.list_base.Presenter
import com.jgarin.githubtop.ui.list_base.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_repos_list.*
import java.lang.IllegalStateException

class ContributorsListFragment : Fragment(), Presenter.ItemClickListener<Contributor, ContributorPresenter.ViewHolder> {

	private val navigator: Navigator
		get() = activity as? Navigator
		        ?: throw IllegalStateException("Parent activity must implement the ${Navigator::class.java.simpleName} interface")

	private val viewModel by lazy {
		ViewModelProviders.of(
				activity ?: throw IllegalStateException("Not bound to an activity!"),
				ContributorsListViewModel.Factory((activity!!.application as ContributorsListApplication).api)
		)
				.get(ContributorsListViewModel::class.java)
	}

	private val adapter by lazy {
		val callback = object : DiffUtil.ItemCallback<Contributor>() {
			override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor) = oldItem.id == newItem.id
			override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor) = oldItem == newItem
		}
		DiffListAdapter(ContributorPresenter(this), callback)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.viewState.observeNotNull(this, ::renderState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_repos_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
		recyclerView.addItemDecoration(SpaceItemDecoration(recyclerView.context, R.dimen.list_item_spacing))
		recyclerView.adapter = adapter
	}

	private fun renderState(state: ContributorsListViewModel.State) {
		swipeRefresh.isRefreshing = state.isLoading

		adapter.submitList(state.contributors)

		state.error?.content?.message?.let { snackbar(it) }
	}

	override fun onItemClicked(viewHolder: ContributorPresenter.ViewHolder, item: Contributor, view: View) {
		navigator.showDetails(item.id)
	}

	companion object {
		fun newInstance() = ContributorsListFragment()
	}

}