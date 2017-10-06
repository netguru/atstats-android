package co.netguru.android.atstats.feature.channels

import co.netguru.android.atstats.RxSchedulersOverrideRule

import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.channels.ChannelsDao
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.FilterController
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
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
        private const val POSITION = 0
        private val MOCKED_FILTER_OPTION = ChannelsFilterOption.MOST_ACTIVE_CHANNEL
        private val CHANNEL_STAT = ChannelStatistics("", "", 2, 1,1, 1)
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
        whenever(channelsDao.getAllChannels()).thenReturn(Single.just(listOf(CHANNEL_STAT)))

        view = mock(ChannelsContract.View::class.java)

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
    fun `should show channel details when getting most active channels successful`() {
        //when
        channelsPresenter.onChannelClick(POSITION)
        //then
        verify(view).showChannelDetails(POSITION, MOCKED_FILTER_OPTION)
    }

    @After
    fun tearDown() {
        channelsPresenter.detachView(false)
    }
}