package com.jgarin.githubtop.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.jgarin.githubtop.Resource
import io.reactivex.Flowable
import io.reactivex.Single

fun AppCompatActivity.fragmentTransaction(transaction: FragmentTransaction.() -> Unit) {
	supportFragmentManager.beginTransaction().apply(transaction).commit()
}

fun Fragment.snackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
	Snackbar.make(
			this.view
			?: throw IllegalStateException("Fragment does not have a view!"),
			message,
			length
	).show()
}

fun <T, R> Flowable<T>.forkJoin(vararg func: Flowable<T>.() -> Flowable<R>): Flowable<R> {
	return publish { shared ->
		Flowable.merge(func.map { shared.it() })
	}
}

fun <T> Single<T>.wrapWithResource(): Flowable<Resource<T>> {
	return flatMapPublisher { Flowable.just(Resource(it, Resource.Status.Success)) }
			.startWith(Resource<T>(null, Resource.Status.Loading))
			.onErrorReturn { Resource(null, Resource.Status.Error(it)) }
}

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, observer: (T) -> Unit) {
	observe(owner, Observer<T> { if (it != null) observer(it) })
}