package concurrency

import io.spherelabs.meteorviewmodel.concurrency.AtomicBoolean

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AtomicBooleanTest {

    @Test
    fun atomicBoolean() {
        val atomic = AtomicBoolean(false)
        assertFalse(atomic.value)
        atomic.value = true
        assertTrue(atomic.value)

        assertFalse(atomic.compareAndSet(expected = false, new = false))
        assertTrue(atomic.value)
        assertTrue(atomic.compareAndSet(expected = true, new = false))
        assertFalse(atomic.value)
    }
}
