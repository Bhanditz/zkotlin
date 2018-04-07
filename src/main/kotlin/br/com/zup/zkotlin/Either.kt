package br.com.zup.zkotlin

interface IEither<out L, out R> {
    fun isLeft(): Boolean
    fun isRight(): Boolean
    operator fun component1(): L?
    operator fun component2(): R?
}

sealed class Either<out L, out R> : IEither<L, R> {

    companion object {
        @JvmStatic
        fun <L> left(left: L): Left<L, Nothing> = Left(left)

        @JvmStatic
        fun <R> right(right: R): Right<Nothing, R> = Right(right)
    }


    fun <X> fold(fl: (L) -> X, fr: (R) -> X): X = when (this) {
        is Left -> fl(left)
        is Right -> fr(right)
    }

}


class Left<out L, out R>(val left: L) : Either<L, R>() {
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

class Right<out L, out R>(val right: R) : Either<L, R>() {
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
