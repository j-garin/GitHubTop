package com.jgarin.githubtop.api

import com.jgarin.githubtop.model.Contributor
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

	@GET("repos/scala/scala/contributors?q=contributions&order=desc") // TODO add restriction to load only 25 records
	fun getTopContributors(): Single<List<Contributor>>

}