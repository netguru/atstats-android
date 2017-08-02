package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.model.ChannelHistory
import co.netguru.android.socialslack.data.channels.model.ChannelList
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import co.netguru.android.socialslack.data.channels.model.*
import io.reactivex.Single
import org.junit.Before
import org.mockito.Mockito.*
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers

@Suppress("IllegalIdentifier")
class ChannelsProviderImplTest {

    companion object {
        private const val CHANNEL = "channel"
        private const val LATEST = "1000"
        private const val USER = "user"

        private val SUCCESSFUL_FILE_OBJECT = FileUploadResponse(true, "",
                FileObject("", "", "", ""))

        private val FAILED_FILE_OBJECT = FileUploadResponse(false, "no_content",
                FileObject("", "", "", ""))
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsApi: ChannelsApi = mock(ChannelsApi::class.java)

    val channelsDao: ChannelsDao = mock(ChannelsDao::class.java)

    lateinit var channelsProvider: ChannelsProvider

    @Before
    fun setUp() {
        val channelList = ChannelList(true, listOf())
        val channelHistory = ChannelHistory(true, listOf(ChannelMessage(1, ChannelMessage.MESSAGE_TYPE, CHANNEL, LATEST, USER, USER)), false)
        whenever(channelsApi.getChannelsList()).thenReturn(Single.just(channelList))
        whenever(channelsApi.getChannelsHistory(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()))
                .thenReturn(Single.just(channelHistory))

        channelsProvider = ChannelsProviderImpl(channelsApi, channelsDao)
    }

    @Test
    fun `should get channels list from api when getting channels list`() {
        //when
        val testObserver = channelsProvider.getChannelsList().test()
        //then
        verify(channelsApi).getChannelsList()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should upload file to api when uploading file to channel`() {
        //given
        whenever(channelsApi.uploadFileToChannel(anyString(), anyObject())).thenReturn(Single.just(SUCCESSFUL_FILE_OBJECT))
        //when
        val testObserver = channelsProvider.uploadFileToChannel("channel", byteArrayOf()).test()
        //then
        verify(channelsApi).uploadFileToChannel(anyString(), anyObject())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should return error when uploading file to channel not successful`() {
        //given
        whenever(channelsApi.uploadFileToChannel(anyString(), anyObject())).thenReturn(Single.just(FAILED_FILE_OBJECT))
        //when
        val testObserver = channelsProvider.uploadFileToChannel("channel", byteArrayOf()).test()
        //then
        testObserver.assertError(Throwable::class.java)
    }
}