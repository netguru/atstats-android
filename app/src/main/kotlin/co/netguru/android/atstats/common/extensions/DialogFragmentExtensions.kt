package co.netguru.android.atstats.common.extensions

import android.support.v4.app.DialogFragment

fun DialogFragment.setStyle(style: Int) {
    // According to doc the magic number 0 relates to an appropriate theme (based on the style)
    setStyle(style, 0)
}