package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent
import javax.inject.Named


@FragmentScope
@Subcomponent(modules = arrayOf(ChannelProfileModule::class))
interface ChannelProfileComponent {

    fun getPresenter(): ChannelProfilePresenter

    // TODO 27.07.2017 place this method in user component
    @Named("UserId")
    fun getUserId(): String
}