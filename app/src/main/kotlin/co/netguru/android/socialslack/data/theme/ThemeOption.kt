package co.netguru.android.socialslack.data.theme

import android.support.annotation.StringRes
import co.netguru.android.socialslack.R


enum class ThemeOption constructor(@StringRes val textResId: Int) {

    COLORFUL(R.string.colourful),
    NETGURU(R.string.netguru);

}