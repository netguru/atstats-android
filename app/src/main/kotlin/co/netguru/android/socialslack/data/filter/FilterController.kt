package co.netguru.android.socialslack.data.filter

import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
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