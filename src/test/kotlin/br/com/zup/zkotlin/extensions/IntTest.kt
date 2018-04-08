package br.com.zup.zkotlin.extensions

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IntTest {

    @Test
    fun isEven() {
        assertTrue { 0.isEven()  }
        assertTrue { 2.isEven()  }
        assertTrue { 4.isEven()  }
        assertFalse { 5.isEven()  }
    }

    @Test
    fun isOdd() {
        assertTrue { 1.isOdd()  }
        assertTrue { 3.isOdd()  }
        assertTrue { 5.isOdd()  }
        assertFalse { 0.isOdd()  }
    }
}
