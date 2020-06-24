package unit

import org.junit.Test

class MyUnitTest {
    @Test
    fun `max int should be greater than 0`() {
        assert(Int.MAX_VALUE > 0)
    }
}