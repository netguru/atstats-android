package co.netguru.android.socialslack.common.extensions

import android.os.Bundle
import android.os.Parcelable
import java.util.*

inline fun <reified T : Parcelable> Bundle.getParcelableCastedArray(key: String): Array<T> {
    val parcelableArray = getParcelableArray(key)
    return Arrays.copyOf(parcelableArray, parcelableArray.size, Array<T>::class.java)
}