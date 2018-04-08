package br.com.zup.zkotlin.either

import br.com.zup.zkotlin.extensions.isEven
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EitherTest {

    @Test
    fun `should get value from Right`() {
        val either = Either.right<Exception, String>("this is rightOf")

        assertTrue { either.isRight() }
        assertFalse { either.isLeft() }
        assertEquals(null, either.component1())
        assertEquals("this is rightOf", either.component2())
        assertEquals("this is rightOf", either.get())
    }

    @Test
    fun `should get value from Left`() {
        val either = Either.left<FailureException, String>(FailureException(-1))

        assertTrue { either.isLeft() }
        assertFalse { either.isRight() }
        assertEquals(-1, either.component1().code)
        assertEquals(null, either.component2())
        assertEquals(-1, either.failure().code)
    }

    @Test
    fun `should deconstruct with component1() and component2()`() {
        val (lComponent1, lComponent2) = Either.leftOf(-1)
        assertEquals(-1, lComponent1)
        assertEquals(null, lComponent2)


        val (rComponent1, rComponent2) = Either.rightOf("this is rightOf")
        assertEquals(null, rComponent1)
        assertEquals("this is rightOf", rComponent2)
    }

    @Test
    fun `should get value from failure() and exception from get() if is Left`() {
        val either = Either.leftOf(-1)
        assertEquals(-1, either.failure())

        assertFailsWith<NoSuchElementException> {
            either.get()
        }
    }

    @Test
    fun `should get value from get() and exception from failure() if is Right`() {
        val either = Either.rightOf("rightOf value")
        assertEquals("rightOf value", either.get())

        assertFailsWith<NoSuchElementException> {
            either.failure()
        }
    }

    @Test
    fun `should only execute the lambda function for success if is Right`() {
        val either = Either.rightOf("right value")

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
    fun `should only execute the lambda function for failure if is Left`() {
        val either = Either.leftOf(-1)

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
    fun `should fold and execute only the right lambda if is Right`() {
        val either = Either.rightOf(2)

        val result = either.fold(
                { -1 }, { it + it })

        assertEquals(4, result)
    }

    @Test
    fun `should fold and execute only the left lambda if is Left`() {
        val either = Either.leftOf(NoSuchElementException("error"))

        val result = either.fold(
                { it.message }, { "not executed" })

        assertEquals("error", result)
    }


    @Test
    fun `should ping-pong left and right with foldCompose()`() {

        assertTrue {
            processEvenOrFailIfIsOdd(0)
                    .foldCompose(::recoveryIfCodeIsEvenOrFailAgain, ::processEvenOrFailIfIsOdd)
                    .foldCompose(::recoveryIfCodeIsEvenOrFailAgain, ::processEvenOrFailIfIsOdd)
                    .fold({ false }, { true })
        }
        assertFalse {
            processEvenOrFailIfIsOdd(0)
                    .foldCompose(::recoveryIfCodeIsEvenOrFailAgain, ::processEvenOrFailIfIsOdd)
                    .foldCompose(::recoveryIfCodeIsEvenOrFailAgain, ::processEvenOrFailIfIsOdd)
                    .foldCompose(::recoveryIfCodeIsEvenOrFailAgain, ::processEvenOrFailIfIsOdd)
                    .fold({ false }, { true })
        }

    }

    @Test
    fun `should call getOrElse until the last operation`() {

        assertEquals(2,
                     processEvenOrFailIfIsOdd(1)
                             .getOrElse { processEvenOrFailIfIsOdd(1) }
                             .getOrElse { processEvenOrFailIfIsOdd(1) }
                             .getOrElse {  Either.right(2) }
                             .get() )


    }

    @Test
    fun `should call getOrElse only once`() {

        assertEquals(2,
                     processEvenOrFailIfIsOdd(1)
                             .getOrElse {  Either.right(2) }
                             .getOrElse { processEvenOrFailIfIsOdd(1) }
                             .getOrElse { processEvenOrFailIfIsOdd(1) }
                             .get() )

    }


}


class FailureException(val code: Int) : RuntimeException("some error code: $code")


fun processEvenOrFailIfIsOdd(code: Int): Either<FailureException, Int> {
    return if (code.isEven()) {
        Either.right<FailureException, Int>(code.inc())
                .also { println("code: $code. I'm good, keep going") }
    } else {
        Either.left<FailureException, Int>(FailureException(code.inc()))
                .also { println("code: $code. I'm not good, I'm going to fail") }
    }
}

fun recoveryIfCodeIsEvenOrFailAgain(e: FailureException): Either<FailureException, Int> {
    return if (e.code.isEven()) {
        Either.right<FailureException, Int>(e.code.inc())
                .also { println("code ${e.code}. I'm good now, keep going") }
    } else {
        Either.left<FailureException, Int>(FailureException(e.code.inc()))
                .also { println("code ${e.code}. I'm not good, I'm going to fail") }
    }
}