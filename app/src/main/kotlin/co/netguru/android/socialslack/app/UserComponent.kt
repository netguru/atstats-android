package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.feature.channels.ChannelsComponent
import co.netguru.android.socialslack.feature.channels.profile.ChannelProfileComponent
import co.netguru.android.socialslack.feature.fetch.FetchComponent
import co.netguru.android.socialslack.feature.filter.FilterComponent
import co.netguru.android.socialslack.feature.home.users.HomeUsersComponent
import co.netguru.android.socialslack.feature.main.MainComponent
import co.netguru.android.socialslack.feature.profile.ProfileComponent
import co.netguru.android.socialslack.feature.share.ShareComponent
import co.netguru.android.socialslack.feature.users.UsersComponent
import co.netguru.android.socialslack.feature.users.profile.UsersProfileComponent

import dagger.Subcomponent

@UserScope
@Subcomponent(modules = arrayOf(LocalRepositoryModule::class))
interface UserComponent {

    fun plusMainComponent(): MainComponent

    fun plusFetchComponent(): FetchComponent

    fun plusChannelsComponent(): ChannelsComponent

    fun plusChannelProfileComponent(): ChannelProfileComponent

    fun plusChannelShareComponent(): ShareComponent

    fun plusFilterComponent(): FilterComponent

    fun plusHomeUsersComponent(): HomeUsersComponent

    fun plusUsersComponent(): UsersComponent

    fun plusUsersProfileComponent(): UsersProfileComponent

    fun plusProfileComponent(): ProfileComponent

    @Subcomponent.Builder
    interface Builder {
        fun localRepositoryModule(localRepositoryModule: LocalRepositoryModule): UserComponent.Builder

        fun build(): UserComponent
    }
}