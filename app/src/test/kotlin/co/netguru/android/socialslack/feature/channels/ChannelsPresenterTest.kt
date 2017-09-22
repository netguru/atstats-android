package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.RxSchedulersOverrideRule

import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@Suppress("IllegalIdentifier")
class ChannelsPresenterTest {

    companion object {
        private val CHANNEL_MOST_ACTIVE = ChannelStatistics("1", "", 10, 1, 3, 1)
        private val CHANNEL1 = ChannelStatistics("11", "", 10, 5, 5, 5)
        private val CHANNEL2 = ChannelStatistics("2", "", 6, 5, 5, 5)
        private val CHANNEL3 = ChannelStatistics("3", "", 4, 5, 5, 5)
        private val CHANNEL33 = ChannelStatistics("33", "", 4, 5, 5, 5)
        private val CHANNEL4 = ChannelStatistics("4", "", 2, 5, 5, 5)
        private val MOCKED_FILTER_OPTION = ChannelsFilterOption.MOST_ACTIVE_CHANNEL
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsDao = mock<ChannelsDao>()
    val filterController = mock<FilterController>()
    lateinit var view: ChannelsContract.View

    lateinit var channelsPresenter: ChannelsPresenter

    @Before
    fun setUp() {
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.just(MOCKED_FILTER_OPTION))

        CHANNEL_MOST_ACTIVE.currentPositionInList = 1
        CHANNEL1.currentPositionInList = 1
        CHANNEL2.currentPositionInList = 2
        CHANNEL3.currentPositionInList = 3
        CHANNEL33.currentPositionInList = 3
        CHANNEL4.currentPositionInList = 4

        view = mock(ChannelsContract.View::class.java)
        whenever(channelsDao.getAllChannels()).thenReturn(Single.just(listOf()))

        channelsPresenter = ChannelsPresenter(channelsDao, filterController)
        channelsPresenter.attachView(view)
    }

    @Test
    fun `should show channels list for user when getting channels successful`() {
        //when
        channelsPresenter.getChannels()
        //then
        verify(view).showChannels(anyObject())
    }

    @Test
    fun `should show error when getting channels from server failed`() {
        //given
        whenever(channelsDao.getAllChannels()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.getChannels()
        //then
        verify(view).showError()
    }

    @Test
    fun `should show loading view when getting channels from server`() {
        //when
        channelsPresenter.getChannels()
        //then
        verify(view).showLoadingView()
    }

    @Test
    fun `should hide loading view when getting channels from server successful`() {
        //given
        whenever(channelsDao.getAllChannels()).thenReturn(Single.just(listOf()))
        //when
        channelsPresenter.getChannels()
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should hide loading view when getting channels from server failed`() {
        //given
        whenever(channelsDao.getAllChannels()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.getChannels()
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should set current filter option when getting filter option successful`() {
        //when
        channelsPresenter.getCurrentFilterOption()
        //then
        verify(view).setCurrentFilterOption(anyObject())
    }

    @Test
    fun `should show filter option error when getting filter option failed`() {
        //given
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.getCurrentFilterOption()
        //then
        verify(view).showFilterOptionError()
    }

    @Test
    fun `should show filter view when filter button clicked`() {
        //when
        channelsPresenter.filterButtonClicked()
        //then
        verify(view).showFilterView()
    }

    @Test
    fun `should show search view when search button clicked`() {
        //when
        channelsPresenter.searchButtonClicked()
        //then
        verify(view).showSearchView()
    }

    @Test
    fun `should get filter option from from filter controller when sort request received`() {
        //when
        channelsPresenter.sortRequestReceived()
        //then
        verify(filterController).getChannelsFilterOption()
    }

    @Test
    fun `should show sorted channels list when sort request successful`() {
        //when
        channelsPresenter.sortRequestReceived()
        //then
        verify(view).showChannels(anyObject())
    }

    @Test
    fun `should show updated filter option when sort request successful`() {
        //when
        channelsPresenter.sortRequestReceived()
        //then
        verify(view).setCurrentFilterOption(anyObject())
    }

    @Test
    fun `should show filter option error when sort request failed`() {
        //given
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.sortRequestReceived()
        //then
        verify(view).showFilterOptionError()
    }

    @Test
    fun `should show loading view when sort request received`() {
        //when
        channelsPresenter.sortRequestReceived()
        //then
        verify(view).showLoadingView()
    }

    @Test
    fun `should hide loading view when sort request received successful`() {
        //when
        channelsPresenter.sortRequestReceived()
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should hide loading view when sort request received failed`() {
        //given
        whenever(filterController.getChannelsFilterOption()).thenReturn(Single.error(Throwable()))
        //when
        channelsPresenter.sortRequestReceived()
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should get channels filter option when on channel click`() {
        //when
        channelsPresenter.onChannelClick(0, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //then
        verify(filterController).getChannelsFilterOption()
    }

    @Test
    fun `should show channel details when getting most active channels successful`() {
        //when
        channelsPresenter.onChannelClick(0, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //then
        verify(view).showChannelDetails(anyObject(), anyObject(), anyObject())
    }

    @Test
    fun `should show selected channel details with 3 top channels from list when current filter option is most active`() {
        //when
        channelsPresenter.onChannelClick(0, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //then
        verify(view).showChannelDetails(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3), MOCKED_FILTER_OPTION)
    }

    @Test
    fun `should change selected channel position in most active list when other channel has the same messages number and it's position is higher`() {
        //when
        channelsPresenter.onChannelClick(1, listOf(CHANNEL1, CHANNEL_MOST_ACTIVE, CHANNEL2, CHANNEL3, CHANNEL4))
        //verify
        verify(view).showChannelDetails(CHANNEL_MOST_ACTIVE, listOf(CHANNEL_MOST_ACTIVE, CHANNEL1, CHANNEL2), MOCKED_FILTER_OPTION)
    }

    @Test
    fun `should add selected channel to most active list when missing and last item from list has the same current position number`() {
        //when
        channelsPresenter.onChannelClick(4, listOf(CHANNEL1, CHANNEL2, CHANNEL3, CHANNEL4, CHANNEL33))
        //verify
        verify(view).showChannelDetails(CHANNEL33, listOf(CHANNEL1, CHANNEL2, CHANNEL33), MOCKED_FILTER_OPTION)
    }

    @After
    fun tearDown() {
        channelsPresenter.detachView(false)
    }
}