package co.netguru.android.atstats

import org.mockito.Mockito

object TestHelper {

    @Suppress("DEPRECATION")
    fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    fun <T> whenever(mock: T) = Mockito.`when`(mock)!!
}