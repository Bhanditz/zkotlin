package br.com.zup.zkotlin.currying

import org.junit.Assert.*
import org.junit.Test

class CurryingTest {

    @Test
    fun `2 parameters`() {
        assertEquals(4, sum2(2,2))

        val sum2Curried = ::sum2.curried()

        assertEquals(7, sum2Curried(2)(5))
    }

    @Test
    fun `3 parameters`() {
        assertEquals(6, sum3(2,2, 2))

        val sum3Curried = ::sum3.curried()

        assertEquals(7, sum3Curried(2)(2)(3))
    }


    @Test
    fun `4 parameters`() {
        assertEquals(6, sum4(2,2, 1,1))

        val sum4Curried = ::sum4.curried()

        assertEquals(7, sum4Curried(2)(2)(2)(1))
    }

    @Test
    fun `5 parameters`() {
        assertEquals(6, sum5(2,1, 1,1, 1))

        val sum5Curried = ::sum5.curried()

        assertEquals(7, sum5Curried(2)(2)(1)(1)(1))
    }


    private fun sum2(a: Int, b: Int) = a + b
    private fun sum3(a: Int, b: Int, c: Int) = a + b + c
    private fun sum4(a: Int, b: Int, c: Int, d: Int) = a + b + c + d
    private fun sum5(a: Int, b: Int, c: Int, d: Int, e: Int) = a + b + c + d + e

}