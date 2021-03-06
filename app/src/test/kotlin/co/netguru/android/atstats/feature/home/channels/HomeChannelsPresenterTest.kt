package co.netguru.android.atstats.feature.home.channels

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.channels.ChannelsDao
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@Suppress("IllegalIdentifier")
class HomeChannelsPresenterTest {

    companion object {
        private val CHANNEL1 = ChannelStatistics("11", "", 10, 5, 0, 1)
        private val CHANNEL2 = ChannelStatistics("2", "", 6, 5, 1, 0)
        private val CHANNEL3 = ChannelStatistics("3", "", 4, 0, 2, 3)
        private val CHANNEL4 = ChannelStatistics("4", "", 2, 0, 3, 2)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsDao = mock<ChannelsDao>()

    val view = mock<HomeChannelsContract.View>()

    lateinit var presenter: HomeChannelsPresenter

    @Before
    fun setup() {
        whenever(channelsDao.getAllChannels()).thenReturn(Single.just(
                listOf(CHANNEL1, CHANNEL2, CHANNEL3, CHANNEL4)
        ))
        presenter = HomeChannelsPresenter(channelsDao)
        presenter.attachView(view)
    }

    @Test
    fun `should show the most active channels when view is attached`() {
        //then
        verify(view).showMostActiveChannels(listOf(CHANNEL1, CHANNEL2, CHANNEL3),
                ChannelsFilterOption.MOST_ACTIVE_CHANNEL)
    }

    @Test
    fun `should show the channels where we are mention the most when the view is attached`() {
        //then
        verify(view).showChannelsWeAreMentionTheMost(listOf(CHANNEL4, CHANNEL3, CHANNEL2),
                ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST)
    }

    @Test
    fun `should show the channels where we are most active when the view is attached`() {
        //then
        verify(view).showChannelsWeAreMostActive(listOf(CHANNEL3, CHANNEL4, CHANNEL1),
                ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE)
    }

    @Test
    fun `should show error when the ChannelsDao returns an error`() {
        //given
        whenever(channelsDao.getAllChannels()).thenReturn(Single.error(Throwable()))
        //when
        presenter.attachView(view)
        //then
        // One for every filter
        verify(view, times(3)).showErrorSortingChannels()
    }

    @After
    fun tearDown() {
        presenter.detachView(false)
    }
}