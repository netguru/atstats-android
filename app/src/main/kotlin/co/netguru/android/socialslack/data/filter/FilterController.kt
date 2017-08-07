package co.netguru.android.socialslack.data.filter

import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import io.reactivex.Single
import javax.inject.Inject

@UserScope
class FilterController @Inject constructor(private val filterOptionRepository: FilterOptionRepository) {

    fun getChannelsFilterOption(): Single<ChannelsFilterOption> =
            Single.just(filterOptionRepository.getChannelsFilterOption())

    fun saveChannelsFilterOption(channelsFilterOption: ChannelsFilterOption) =
            filterOptionRepository.saveChannelsFilterOption(channelsFilterOption)
}