package co.netguru.android.socialslack.feature.search

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.search.adapter.SearchItemType
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

@FragmentScope
class SearchPresenter @Inject constructor()
    : MvpNullObjectBasePresenter<SearchContract.View>(), SearchContract.Presenter {

    companion object {
        private const val MOCKED_ITEMS_COUNT = 50
    }

    override fun searchItemReceived(searchItemType: SearchItemType) {
        when (searchItemType) {
            SearchItemType.USERS -> view.initUsersSearchView(getMockedUsers())
            SearchItemType.CHANNELS -> view.initChannelSearchView(getMockedChannels())
        }
    }

    //TODO 29.08.2017 Remove those functions when integrating API
    private fun getMockedUsers() = 0.until(MOCKED_ITEMS_COUNT).map {
        UserStatistic("", "user $it", "", "", "User User$it",
                0, 0, (1000 + it), 0, null
        )
    }

    private fun getMockedChannels() = 0.until(MOCKED_ITEMS_COUNT).map {
        ChannelStatistics("", "channel $it", (1000 + it), 0, 0, 0)
    }
}