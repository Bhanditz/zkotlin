package br.com.zup.zkotlin.either

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EitherListTest {

    private val eitherList = listOf<Either<String, Int>>(
            Either.right(2),
            Either.right(4),
            Either.left("error 3"),
            Either.left("error 4"))

    @Test
    fun `fold either list`() {
        val rightList = eitherList.fold(
                { assertTrue { listOf("error 3", "error 4").contains(it) } },
                { it * 2 })

        assertEquals(listOf(4, 8), rightList)
    }

    @Test
    fun `fold left only and return same either list`() {
        val sameList = eitherList.foldError {
            assertTrue { listOf("error 3", "error 4").contains(it) }
        }

        assertEquals(eitherList, sameList)
    }

    @Test
    fun `fold right only and return a new list with right values only`() {
        val doubleList = eitherList.foldSuccess {
            it * 2
        }

        assertEquals(listOf(4, 8), doubleList)
    }

    @Test
    fun `fold right and collect right values without transformation`() {

        assertEquals(listOf(2, 4), eitherList.foldSuccess())
    }



}