package br.com.zup.zkotlin.either


inline fun <L, R, T> List<Either<L, R>>.fold(onError: (L) -> Unit, onSuccess: (R) -> T): List<T> =
        foldError(onError)
        .run { foldSuccess(onSuccess) }

inline fun <L, R> List<Either<L, R>>.foldError(onError: (L) -> Unit): List<Either<L, R>> {
    this.filter { it.isLeft() }
            .forEach { onError(it.failure()) }
    return this
}

inline fun <L, R, T> List<Either<L, R>>.foldSuccess(onSuccess: (R) -> T): List<T> =
        this.filter { it.isRight() }
                .map { onSuccess(it.get()) }

fun <L, R> List<Either<L, R>>.foldSuccess(): List<R> =
        this.foldSuccess { it }
