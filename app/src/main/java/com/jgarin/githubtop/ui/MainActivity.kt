package com.jgarin.githubtop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jgarin.githubtop.ui.list.ContributorsListFragment
import com.jgarin.githubtop.R
import com.jgarin.githubtop.extensions.fragmentTransaction
import com.jgarin.githubtop.ui.details.ContributorDetailsFragment

class MainActivity : AppCompatActivity(), Navigator {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.container)

		if (savedInstanceState == null) home()
	}

	override fun home() {
		fragmentTransaction {
			replace(R.id.fragmentContainer, ContributorsListFragment.newInstance())
		}
	}

	override fun showDetails(id: Long) {
		fragmentTransaction {
			replace(R.id.fragmentContainer, ContributorDetailsFragment.newInstance(id))
			addToBackStack("details")
		}
	}

}
