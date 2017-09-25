package co.netguru.android.atstats.data.filter

import co.netguru.android.atstats.app.scope.UserScope
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import io.reactivex.Single
import javax.inject.Inject

@UserScope
class FilterController @Inject constructor(private val filterOptionRepository: FilterOptionRepository) {

    fun getChannelsFilterOption(): Single<ChannelsFilterOption> =
            Single.just(filterOptionRepository.getChannelsFilterOption())

    fun saveChannelsFilterOption(channelsFilterOption: ChannelsFilterOption) =
            filterOptionRepository.saveChannelsFilterOption(channelsFilterOption)

    fun getUsersFilterOption(): Single<UsersFilterOption> =
            Single.just(filterOptionRepository.getUsersFilterOption())

    fun saveUsersFilterOption(usersFilterOption: UsersFilterOption) =
            filterOptionRepository.saveUsersFilterOption(usersFilterOption)
}