package br.com.zup.zkotlin.extensions

fun Int.isEven() = this % 2 == 0

fun Int.isOdd() = !isEven()
