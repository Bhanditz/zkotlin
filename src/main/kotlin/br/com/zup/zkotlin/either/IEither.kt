package br.com.zup.zkotlin.either

interface IEither<out L, out R> {
    fun isLeft(): Boolean
    fun isRight(): Boolean
    operator fun component1(): L?
    operator fun component2(): R?
}