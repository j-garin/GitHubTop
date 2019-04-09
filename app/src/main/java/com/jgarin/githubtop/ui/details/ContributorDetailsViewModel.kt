package com.jgarin.githubtop.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jgarin.githubtop.api.Api
import java.lang.IllegalArgumentException

class ContributorDetailsViewModel(private val contributorId: Long, private val api: Api) : ViewModel() {

	class Factory(private val contributorId: Long, private val api: Api) : ViewModelProvider.Factory {
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return if (modelClass.isAssignableFrom(ContributorDetailsViewModel::class.java)) ContributorDetailsViewModel(contributorId, api) as T
			else throw IllegalArgumentException("Unable to create instance of ${modelClass.simpleName}")
		}

	}

}