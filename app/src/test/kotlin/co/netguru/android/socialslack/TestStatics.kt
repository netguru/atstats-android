package co.netguru.android.socialslack

import org.mockito.Mockito

object TestStatics {

    @Suppress("DEPRECATION")
    fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }
}