package com.jgarin.githubtop.wrappers

/**
 * A wrapper class that returns the content only if it hasn't been handled yet
 */
class SingleLiveEvent<out T>(private val _content: T) {

    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    val content: T?
        get() {
            return when {
                hasBeenHandled -> null
                else -> {
                    hasBeenHandled = true
                    _content
                }
            }
        }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = _content
}