package co.netguru.android.socialslack.data.filter.model

import android.support.annotation.StringRes
import co.netguru.android.socialslack.R

enum class UsersFilterOption(val value: String, @StringRes val textResId: Int) {

    PERSON_WHO_WE_WRITE_THE_MOST("personWhoWeWriteTheMost", R.string.person_we_write_most),
    PERSON_WHO_WRITES_TO_US_THE_MOST("personWhoWritesToUsTheMost", R.string.person_writes_to_us_most),
    PERSON_WHO_WE_TALK_THE_MOST("personWhoWeTalkTheMost", R.string.person_we_talk_most);

    companion object {

        fun getEnumForValue(value: String): UsersFilterOption {
            UsersFilterOption.values().forEach {
                if (it.value == value) {
                    return it
                }
            }

            throw IllegalArgumentException("There is no enum for $value")
        }
    }
}