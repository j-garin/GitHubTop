package com.jgarin.githubtop.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jgarin.githubtop.Resource
import com.jgarin.githubtop.SingleLiveEvent
import com.jgarin.githubtop.api.Api
import com.jgarin.githubtop.extensions.forkJoin
import com.jgarin.githubtop.extensions.wrapWithResource
import com.jgarin.githubtop.model.Contributor
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.lang.IllegalArgumentException

class ContributorsListViewModel(private val api: Api) : ViewModel() {

	private val actions = BehaviorSubject.create<Action>()

	private val stateStream = actions
			.toFlowable(BackpressureStrategy.BUFFER)
			.forkJoin(
					{
						ofType(Action.GetContributors::class.java)
								.switchMap {
									api.getTopContributors()
											.subscribeOn(Schedulers.io())   // this should be implemented in data source and repository, but i have little time
											.wrapWithResource()
											.map { Result.GetContributors(it) }
								}
					}
			)
			.scan(State.default) { prev, result ->
				when (result) {
					is Result.GetContributors -> prev.copy(
							isLoading = result.resource.status is Resource.Status.Loading,
							contributors = result.resource.data ?: prev.contributors,
							error = (result.resource.status as? Resource.Status.Error)?.error
									        ?.let { SingleLiveEvent(it) } ?: prev.error
					)
					else                      -> TODO("Result handling not implemented")
				}

			}

	private val stateLiveData = MutableLiveData<State>()

	val viewState: LiveData<State> = stateLiveData

	private val disposable = stateStream
			.subscribe { stateLiveData.postValue(it) }

	init {
		refresh()
	}

	private sealed class Action {
		object GetContributors : Action()
	}

	private sealed class Result {
		class GetContributors(val resource: Resource<List<Contributor>>) : Result()
	}

	override fun onCleared() {
		disposable.dispose()
	}

	fun refresh() {
		actions.onNext(Action.GetContributors)
	}

	data class State(
			val isLoading: Boolean,
			val contributors: List<Contributor>,
			val error: SingleLiveEvent<Throwable>?
	) {
		companion object {
			val default = State(
					isLoading = false,
					contributors = emptyList(),
					error = null
			)
		}
	}

	class Factory(private val api: Api) : ViewModelProvider.Factory {
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return if (modelClass.isAssignableFrom(ContributorsListViewModel::class.java)) ContributorsListViewModel(api) as T
			else throw IllegalArgumentException("Unable to create instance of ${modelClass.simpleName}")
		}

	}

}