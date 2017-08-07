package co.netguru.android.socialslack.feature.fetch

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.data.channels.ChannelsController
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import com.nhaarman.mockito_kotlin.mock
import co.netguru.android.socialslack.TestHelper.whenever
import org.mockito.Mockito.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers


@Suppress("IllegalIdentifier")
class FetchPresenterTest {

    companion object {
        private val CHANNEL1 = Channel("1", "", "", false, false, 1)
        private val CHANNEL2 = Channel("2", "", "", false, false, 1)
        private val CHANNEL3 = Channel("3", "", "", false, false, 1)
        private val CHANNEL4 = Channel("4", "", "", false, false, 1)
        private val CHANNEL5 = Channel("5", "", "", false, false, 1)

        private val CHANNEL_STATISTICS1 = ChannelStatistics("1", "", 10, 5, 5, 5)

    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsController = mock<ChannelsController>()

    val view = mock<FetchContract.View>()

    lateinit var fetchPresenter: FetchPresenter

    @Before
    fun setUp() {
        fetchPresenter = FetchPresenter(channelsController)
    }

    @Test
    fun `should show main activity when no error`() {
        // given
        whenever(channelsController.getChannelsList()).thenReturn(Single.just(listOf(CHANNEL1, CHANNEL2, CHANNEL3, CHANNEL4, CHANNEL5)))
        whenever(channelsController.countChannelStatistics(anyString(), anyString(), anyString())).thenReturn(Single.just(CHANNEL_STATISTICS1))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showMainActivity()
    }

    @Test
    fun `should show error when channel list returns error` () {
        // given
        whenever(channelsController.getChannelsList()).thenReturn(Single.error(Throwable()))
        whenever(channelsController.countChannelStatistics(anyString(), anyString(), anyString())).thenReturn(Single.just(CHANNEL_STATISTICS1))

        // when
        fetchPresenter.attachView(view)

        // then
        verify(view).showErrorMessage()
    }
}