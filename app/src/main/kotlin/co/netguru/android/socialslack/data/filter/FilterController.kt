package co.netguru.android.socialslack.data.filter

import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterController @Inject constructor(private val filterOptionRepository: FilterOptionRepository) {

    fun getChannelsFilterOption() = Single.just(filterOptionRepository.getChannelsFilterOption())

    fun saveChannelsFilterOption(channelsFilterOption: ChannelsFilterOption) =
            filterOptionRepository.saveChannelsFilterOption(channelsFilterOption)
}