package br.com.zup.zkotlin.either

import java.util.*

sealed class Either<L, R> : IEither<L, R> {

    companion object {
        @JvmStatic
        fun <L, R> left(left: L) = Left<L, R>(left)

        @JvmStatic
        fun <L> leftOf(left: L): Left<L, Nothing> = Left(left)

        @JvmStatic
        fun <L, R> right(right: R) = Right<L, R>(right)

        @JvmStatic
        fun <R> rightOf(right: R): Right<Nothing, R> = Right(right)
    }

    fun <T> success(onSuccess: (R) -> T): T = onSuccess(get())

    fun <T> failure(onFail: (L) -> T): T = onFail(failure())

    fun get(): R = when (this) {
        is Right -> this.component2()
        else -> throw NoSuchElementException(this.toString())
    }

    fun failure(): L = when (this) {
        is Left -> this.component1()
        else -> throw NoSuchElementException(this.toString())
    }

    fun <T> fold(left: (L) -> T, right: (R) -> T): T = when (this) {
        is Left -> left(component1())
        is Right -> right(component2())
    }

    fun <TL, TR> foldCompose(left: (L) -> Either<TL, TR>,
                             right: (R) -> Either<TL, TR>): Either<TL, TR> = when (this) {
        is Left -> left(this.component1())
        is Right -> right(this.component2())
    }

    fun getOrElse(supplier: (L) -> Either<L, R>): Either<L, R> = when (this) {
        is Left -> supplier(this.component1())
        is Right -> this
    }
}


class Left<L, R>(private val left: L) : Either<L, R>() {
    override fun isLeft() = true

    override fun isRight() = false

    override fun component1(): L = this.left

    override fun component2(): R? = null

    override fun toString() = "Either.Left($left)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Left<*, *>

        if (left != other.left) return false

        return true
    }

    override fun hashCode(): Int = left?.hashCode() ?: 0

}


class Right<L, R>(private val right: R) : Either<L, R>() {
    override fun isLeft() = false

    override fun isRight() = true

    override fun component1(): L? = null

    override fun component2(): R = right

    override fun toString() = "Either.Right($right)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Right<*, *>

        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int = right?.hashCode() ?: 0
}
