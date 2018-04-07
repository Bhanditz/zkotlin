package br.com.zup.zkotlin.either

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EitherTest {

    @Test
    fun `should get value from Right`() {
        val either = Either.right("this is right")

        assertTrue { either.isRight() }
        assertFalse { either.isLeft() }
        assertEquals(null, either.component1())
        assertEquals("this is right", either.component2())
        assertEquals("this is right", either.get())
    }

    @Test
    fun `should get value from Left`() {
        val either = Either.left(-1)

        assertTrue { either.isLeft() }
        assertFalse { either.isRight() }
        assertEquals(-1, either.component1())
        assertEquals(null, either.component2())
        assertEquals(-1, either.failure())
    }

    @Test
    fun `should deconstruct`() {
        val (lComponent1, lComponent2) = Either.left(-1)
        assertEquals(-1, lComponent1)
        assertEquals(null, lComponent2)


        val (rComponent1, rComponent2) = Either.right("this is right")
        assertEquals(null, rComponent1)
        assertEquals("this is right", rComponent2)
    }

    @Test
    fun `should get value from failure() and exception from get() if is Left`() {
        val either = Either.left(-1)
        assertEquals(-1, either.failure())

        assertFailsWith<NoSuchElementException> {
            either.get()
        }
    }

    @Test
    fun `should get value from get() and exception from failure() if is Right`() {
        val either = Either.right("right value")
        assertEquals("right value", either.get())

        assertFailsWith<NoSuchElementException> {
            either.failure()
        }
    }

    @Test
    fun `should only execute the lambda function for success operation`() {
        val either = Either.right("right value")

        val rightValue = either.success {
            assertEquals("right value", it)
            it + " changed"
        }

        assertEquals("right value changed", rightValue)

        assertFailsWith<NoSuchElementException> {
            either.failure { println("not executed") }
        }
    }

    @Test
    fun `should only execute the lambda function for failure operation`() {
        val either = Either.left(-1)

        val leftValue = either.failure {
            assertEquals(-1, it)
            it * it
        }

        assertEquals(1, leftValue)

        assertFailsWith<NoSuchElementException> {
            either.success { println("not executed") }
        }
    }

    @Test
    fun `should fold right and double it`() {
        val either = Either.right(2)

        val result = either.fold(
                { -1 }, { it + it })

        assertEquals(4, result)
    }

    @Test
    fun `should fold left and return the exception message`() {
        val either = Either.left(NoSuchElementException("error"))

        val result = either.fold(
                { it.message  }, { "not executed" })

        assertEquals("error", result)
    }

}
