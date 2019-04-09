package com.jgarin.githubtop

data class Resource<T>(val data: T?, val status: Status) {

    sealed class Status {
        object Loading : Status()
        object Success : Status()
        data class Error(val error: Throwable) : Status()
    }

}