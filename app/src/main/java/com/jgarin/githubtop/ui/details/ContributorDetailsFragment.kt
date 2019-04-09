package com.jgarin.githubtop.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jgarin.githubtop.R
import com.jgarin.githubtop.ui.ContributorsListApplication
import kotlinx.android.synthetic.main.fragment_details.*
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class ContributorDetailsFragment : Fragment() {

	private val contributorId by lazy {
		arguments?.getLong(ID_KEY)
		?: throw IllegalArgumentException("Required fragment argument contributorId is not set!")
	}

	private val viewModel by lazy {
		ViewModelProviders.of(this, ContributorDetailsViewModel.Factory(
				contributorId,
				(activity?.application as? ContributorsListApplication)?.api
				?: throw IllegalStateException("Not bound to an activity!")
		)).get(ContributorDetailsViewModel::class.java)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_details, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
		toolbar.title = getString(R.string.xx_details, contributorId)
	}

	companion object {
		private val ID_KEY = "contributorId"

		fun newInstance(contributorId: Long): ContributorDetailsFragment {
			val args = Bundle()
					.apply { putLong(ID_KEY, contributorId) }
			return ContributorDetailsFragment().apply { arguments = args }
		}
	}
}