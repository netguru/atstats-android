package co.netguru.android.socialslack.common.util

import android.content.res.Resources

object UnitUtils {

    fun Int.dp(): Float {
        return this * Resources.getSystem().displayMetrics.density
    }
}