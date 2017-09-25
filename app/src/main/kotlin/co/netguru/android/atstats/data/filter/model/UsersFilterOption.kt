package co.netguru.android.atstats.data.filter.model

import android.support.annotation.StringRes
import co.netguru.android.atstats.R

enum class UsersFilterOption(@StringRes val textResId: Int) : Filter {

    PERSON_WHO_WE_WRITE_THE_MOST(R.string.person_we_write_most) {
        override fun filterName() = name
    },
    PERSON_WHO_WRITES_TO_US_THE_MOST(R.string.person_writes_to_us_most) {
        override fun filterName() = name
    },
    PERSON_WHO_WE_TALK_THE_MOST(R.string.person_we_talk_most) {
        override fun filterName() = name
    };
}