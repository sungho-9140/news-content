package com.dev.sungho.business.model

sealed class DomainResult<T> {
    data class Success<T>(val item: T) : DomainResult<T>()

    sealed class Failure<T> : DomainResult<T>()

    data class UnknownFailure<T>(val message: String? = null) : Failure<T>()

    data class Exception<T>(val message: String? = null) : Failure<T>()
}