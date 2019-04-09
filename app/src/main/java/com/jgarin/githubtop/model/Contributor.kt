package com.jgarin.githubtop.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Contributor(
		@JsonProperty("id")
		val id: Long,
		@JsonProperty("avatar_url")
		val avatarUrl: String,
		@JsonProperty("contributions")
		val contributions: Long,
		@JsonProperty("login")
		val name: String
)