package br.com.zup.zkotlin.coroutine

import kotlinx.coroutines.experimental.Deferred

suspend fun <T> List<Deferred<T>>.await(): List<T> = this.map { it.await() }
