package co.netguru.android.atstats.app

import co.netguru.android.atstats.app.scope.UserScope
import co.netguru.android.atstats.feature.channels.ChannelsComponent
import co.netguru.android.atstats.feature.channels.profile.ChannelProfileComponent
import co.netguru.android.atstats.feature.fetch.FetchComponent
import co.netguru.android.atstats.feature.filter.FilterComponent
import co.netguru.android.atstats.feature.home.users.HomeUsersComponent
import co.netguru.android.atstats.feature.home.channels.HomeChannelsComponent
import co.netguru.android.atstats.feature.home.dashboard.HomeDashboardComponent
import co.netguru.android.atstats.feature.profile.ProfileComponent
import co.netguru.android.atstats.feature.search.SearchComponent
import co.netguru.android.atstats.feature.share.ShareComponent
import co.netguru.android.atstats.feature.users.UsersComponent
import co.netguru.android.atstats.feature.users.profile.UsersProfileComponent

import dagger.Subcomponent

@UserScope
@Subcomponent(modules = arrayOf(UserLocalRepositoryModule::class))
interface UserComponent {

    fun plusFetchComponent(): FetchComponent

    fun plusChannelsComponent(): ChannelsComponent

    fun plusChannelProfileComponent(): ChannelProfileComponent

    fun plusChannelShareComponent(): ShareComponent

    fun plusFilterComponent(): FilterComponent

    fun plusHomeUsersComponent(): HomeUsersComponent

    fun plusHomeChannelsComponent(): HomeChannelsComponent

    fun plusHomeDashboardComponent(): HomeDashboardComponent

    fun plusUsersComponent(): UsersComponent

    fun plusUsersProfileComponent(): UsersProfileComponent

    fun plusProfileComponent(): ProfileComponent

    fun plusSearchComponent(): SearchComponent

    @Subcomponent.Builder
    interface Builder {
        fun userLocalRepositoryModule(localRepositoryModule: UserLocalRepositoryModule): UserComponent.Builder

        fun build(): UserComponent
    }
}