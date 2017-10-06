package co.netguru.android.atstats.feature.channels.profile

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.data.channels.ChannelsDao
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import com.nhaarman.mockito_kotlin.mock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@Suppress("IllegalIdentifier")
class ChannelProfilePresenterTest {

    companion object {
        private const val POSITION = 0
        private val CHANNEL1 = ChannelStatistics("1", "", 10, 5, 5, 5)
        private val CHANNEL2 = ChannelStatistics("2", "", 6, 5, 5, 5)
        private val CHANNEL3 = ChannelStatistics("3", "", 4, 5, 5, 5)
        private val CHANNEL33 = ChannelStatistics("33", "", 4, 5, 5, 5)
        private val CHANNEL4 = ChannelStatistics("4", "", 2, 5, 5, 5)
        private val MOCKED_FILTER_OPTION = ChannelsFilterOption.MOST_ACTIVE_CHANNEL
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    lateinit var view: ChannelProfileContract.View

    lateinit var presenter: ChannelProfileContract.Presenter

    @Before
    fun setUp() {
        CHANNEL1.currentPositionInList = 1
        CHANNEL2.currentPositionInList = 2
        CHANNEL3.currentPositionInList = 3
        CHANNEL33.currentPositionInList = 3
        CHANNEL4.currentPositionInList = 4
        view = mock(ChannelProfileContract.View::class.java)

        presenter = ChannelProfilePresenter()
        presenter.attachView(view)
    }

    @Test
    fun `should init view view when preparing view successful`() {
        //when
        presenter.prepareView(listOf(CHANNEL1), POSITION, MOCKED_FILTER_OPTION)
        //then
        verify(view).initView(anyObject(), anyObject())
    }

    @Test
    fun `should scroll to channel position when preparing view successful`() {
        //when
        presenter.prepareView(listOf(CHANNEL1), POSITION, MOCKED_FILTER_OPTION)
        //then
        verify(view).scrollToChannelPosition(POSITION)
    }

    @Test
    fun `should show loading view when the channel message are request`() {
        //when
        presenter.prepareView(listOf(CHANNEL1), POSITION, MOCKED_FILTER_OPTION)
        //then
        verify(view).showLoadingView()
    }

    @Test
    fun `should show hide view when the channel messages call is successful`() {
        //when
        presenter.prepareView(listOf(CHANNEL1), POSITION, MOCKED_FILTER_OPTION)
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should show selected channel with 2 channels that are the most active when on share button clicked`() {
        //when
        presenter.onShareButtonClick(POSITION, listOf(CHANNEL1, CHANNEL2, CHANNEL3))
        //then
        verify(view).showShareDialogFragment(CHANNEL1, listOf(CHANNEL1, CHANNEL2))
    }

    @Test
    fun `should change selected channel position when other channel has the same total messages number but higher position`() {
        //when
        presenter.onShareButtonClick(2, listOf(CHANNEL1, CHANNEL3, CHANNEL33))
        //then
        verify(view).showShareDialogFragment(CHANNEL33, listOf(CHANNEL1, CHANNEL33))
    }

    @Test
    fun `should add selected channel to list when it's missing and it's position equals last item position`() {
        //when
        presenter.onShareButtonClick(3, listOf(CHANNEL1, CHANNEL2, CHANNEL3, CHANNEL33))
        //then
        verify(view).showShareDialogFragment(CHANNEL33, listOf(CHANNEL1, CHANNEL2))
    }

    @Test
    fun `should show share view when on share button clicked and getting sharable list successful`() {
        //when
        presenter.onShareButtonClick(POSITION, listOf(CHANNEL1))
        //then
        verify(view).showShareDialogFragment(anyObject(), anyObject())
    }

    @Test
    fun `should show search view when search button clicked`() {
        //when
        presenter.searchButtonClicked()
        //then
        verify(view).showSearchView()
    }

    @After
    fun tearDown() {
        presenter.detachView(false)
    }

}