package com.abrarshakhi.denapawna.core.utils

sealed interface Outcome<out T, out E> {
    data class Ok<T>(val data: T) : Outcome<T, Nothing>
    data class Err<E>(val error: E) : Outcome<Nothing, E>

    companion object {
        fun <T> ok(data: T): Outcome<T, Nothing> = Ok(data)
        fun <E> err(error: E): Outcome<Nothing, E> = Err(error)
    }
}

inline fun <T> outcomeCatching(block: () -> T): Outcome<T, Throwable> = try {
    Outcome.Ok(block())
} catch (t: Throwable) {
    Outcome.Err(t)
}

fun <T> Outcome<T, *>.isOk(): Boolean = this is Outcome.Ok<T>
fun <E> Outcome<*, E>.isErr(): Boolean = this is Outcome.Err<E>

fun <T> Outcome<T, *>.getOrNull(): T? = (this as? Outcome.Ok)?.data
fun <E> Outcome<*, E>.errorOrNull(): E? = (this as? Outcome.Err)?.error

inline fun <T> Outcome<T, *>.getOrThrow(error: () -> Throwable): T = when (this) {
    is Outcome.Ok -> data
    is Outcome.Err -> throw error()
}

inline fun <T, E> Outcome<T, E>.getOrElse(onError: (E) -> T): T = when (this) {
    is Outcome.Ok -> data
    is Outcome.Err -> onError(error)
}

inline fun <T, E, R> Outcome<T, E>.map(transform: (T) -> R): Outcome<R, E> = when (this) {
    is Outcome.Ok -> Outcome.Ok(transform(data))
    is Outcome.Err -> this
}

inline fun <T, E, F> Outcome<T, E>.mapError(transform: (E) -> F): Outcome<T, F> = when (this) {
    is Outcome.Ok -> this
    is Outcome.Err -> Outcome.Err(transform(error))
}

inline fun <T, E, R> Outcome<T, E>.flatMap(transform: (T) -> Outcome<R, E>): Outcome<R, E> =
    when (this) {
        is Outcome.Ok -> transform(data)
        is Outcome.Err -> this
    }

inline fun <T, E> Outcome<T, E>.recover(transform: (E) -> T): Outcome<T, E> = when (this) {
    is Outcome.Ok -> this
    is Outcome.Err -> Outcome.Ok(transform(error))
}


inline fun <T, E> Outcome<T, E>.onOk(action: (T) -> Unit): Outcome<T, E> = apply {
    if (this is Outcome.Ok) action(data)
}

inline fun <T, E> Outcome<T, E>.onErr(action: (E) -> Unit): Outcome<T, E> = apply {
    if (this is Outcome.Err) action(error)
}

inline fun <T, E, R> Outcome<T, E>.fold(onOk: (T) -> R, onErr: (E) -> R): R = when (this) {
    is Outcome.Ok -> onOk(data)
    is Outcome.Err -> onErr(error)
}