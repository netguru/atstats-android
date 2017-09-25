package co.netguru.android.atstats.data.theme

import android.support.annotation.StringRes
import co.netguru.android.atstats.R


enum class ThemeOption(@StringRes val textResId: Int) {

    COLOURFUL(R.string.colourful),
    NETGURU(R.string.netguru);
}