package br.com.zup.zkotlin.coroutine

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit.MILLISECONDS

class CoroutineListTest {

    @Test
    fun `launch 5 and wait until the end`() {

        val doubleList = runBlocking {
            (1..10).map { x -> asyncDoubleIt(x) }
                    .await()
        }

        assertEquals((1..10).map { it * 2 }, doubleList)

    }

    private fun asyncDoubleIt(x: Int) = async {
        println("starting $x")
        delay(200, MILLISECONDS)
        println("done $x")
        x * 2
    }

}