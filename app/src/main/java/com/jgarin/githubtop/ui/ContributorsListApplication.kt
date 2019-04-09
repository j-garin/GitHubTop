package com.jgarin.githubtop.ui

import android.app.Application
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jgarin.githubtop.api.Api
import retrofit2.converter.jackson.JacksonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class ContributorsListApplication : Application() {

	val api by lazy {
		val client = OkHttpClient.Builder().build()

		val mapper = jacksonObjectMapper()
				.disable(
						DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
				)
				.enable(
						DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,
						DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT
				)
				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)

		val retrofit = Retrofit.Builder()
				.baseUrl("https://api.github.com")
				.client(client)
				.addConverterFactory(JacksonConverterFactory.create(mapper))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()

		retrofit.create(Api::class.java)
	}

	override fun onCreate() {
		super.onCreate()

	}

}