package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@Suppress("IllegalIdentifier")
class ChannelProfilePresenterTest {

    companion object {
        private val CHANNEL_ID = "Channel_id"
        private val CHANNEL = "channel"
        private val HERE_COUNT = 2
        private val MENTIONS_COUNT = 2
        private val MESSAGE_COUNT = 4
        private val CHANNEL_STATISTICS = ChannelStatistics(CHANNEL_ID, CHANNEL, MESSAGE_COUNT, HERE_COUNT, MENTIONS_COUNT, 0)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsDao = mock<ChannelsDao>()
    lateinit var view: ChannelProfileContract.View

    lateinit var presenter: ChannelProfileContract.Presenter

    @Before
    fun setUp() {
        view = mock(ChannelProfileContract.View::class.java)

        presenter = ChannelProfilePresenter(channelsDao)
        presenter.attachView(view)
    }

    @Test
    fun `should show correct number of heres and mentions when getting messages from channel`() {
        // given
        whenever(channelsDao.getChannelById(ArgumentMatchers.anyString()))
                .thenReturn(Single.just(CHANNEL_STATISTICS))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).showChannelInfo(4, 2, 2)
    }

    @Test
    fun `should show an error when error is returned`() {
        // given
        whenever(channelsDao.getChannelById(ArgumentMatchers.anyString()))
                .thenReturn(Single.error(Throwable()))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).showError()
    }

    @Test
    fun `should show loading view when the channel message are request`() {
        // given
        whenever(channelsDao.getChannelById(ArgumentMatchers.anyString()))
                .thenReturn(Single.just(CHANNEL_STATISTICS))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).showLoadingView()
    }

    @Test
    fun `should show hide view when the channel messages call is successful`() {
        // given
        whenever(channelsDao.getChannelById(ArgumentMatchers.anyString()))
                .thenReturn(Single.just(CHANNEL_STATISTICS))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should show hide view when the channel messages call returns error`() {
        // given
        whenever(channelsDao.getChannelById(ArgumentMatchers.anyString()))
                .thenReturn(Single.error(Throwable()))
        // when
        presenter.getChannelInfo(CHANNEL)
        // then
        verify(view).hideLoadingView()
    }

    @After
    fun tearDown() {
        presenter.detachView(false)
    }

}