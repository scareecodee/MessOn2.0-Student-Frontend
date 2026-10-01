package com.messon.student.app.utils
import com.messon.student.app.core.AppError

sealed interface Result<out D, out E> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E>(val error: E) : Result<Nothing, E>
}

typealias EmptyResult<E> = Result<Unit, E>

typealias AppResult<T> = Result<T, AppError>


inline fun <T, E, R> Result<T, E>.map(transform: (T) -> R): Result<R, E> = when (this) {
    is Result.Error -> Result.Error(error)
    is Result.Success -> Result.Success(transform(data))
}

inline fun <T, E, R> Result<T, E>.flatMap(transform: (T) -> Result<R, E>): Result<R, E> = when (this) {
    is Result.Error -> Result.Error(error)
    is Result.Success -> transform(data)
}

fun <T, E> Result<T, E>.asEmptyDataResult(): EmptyResult<E> = map { }


inline fun <T, E> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T, E> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    if (this is Result.Error) action(error)
    return this
}


inline fun <T, E> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> =
    onError(action)


fun <T, E> Result<T, E>.getOrNull(): T? = when (this) {
    is Result.Success -> data
    is Result.Error -> null
}

fun <T, E : Throwable> Result<T, E>.getOrThrow(): T = when (this) {
    is Result.Success -> data
    is Result.Error -> throw error
}

fun <T> Result<T, AppError>.getOrThrowApp(): T = when (this) {
    is Result.Success -> data
    is Result.Error -> throw RuntimeException(error.message, error.cause)
}


inline fun <T> runAppCatching(block: () -> T): Result<T, AppError> = try {
    Result.Success(block())
} catch (e: Exception) {
    Result.Error(AppError.Unknown(e.message ?: "Unknown error", e))
}

suspend inline fun <T> suspendRunAppCatching(
    crossinline block: suspend () -> T
): Result<T, AppError> = try {
    Result.Success(block())
} catch (e: Exception) {
    Result.Error(AppError.Unknown(e.message ?: "Unknown error", e))
}