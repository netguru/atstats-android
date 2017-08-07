package co.netguru.android.socialslack.data.filter

import android.content.SharedPreferences
import co.netguru.android.socialslack.app.LocalRepositoryModule
import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.common.extensions.edit
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Named

@UserScope
class FilterOptionRepository @Inject constructor(@Named(LocalRepositoryModule.FILTER_OPTION_SHARED_PREFERENCES_NAME)
                                                 private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val CHANNELS_FILTER_OPTION = "channels_filter_option"
    }

    fun saveChannelsFilterOption(channelsFilterOption: ChannelsFilterOption): Completable {
        return Completable.fromAction({
            sharedPreferences.edit {
                putString(CHANNELS_FILTER_OPTION, channelsFilterOption.value)
            }
        })
    }

    fun getChannelsFilterOption() = ChannelsFilterOption.getEnumForValue(sharedPreferences
            .getString(CHANNELS_FILTER_OPTION, ChannelsFilterOption.MOST_ACTIVE_CHANNEL.value))
}