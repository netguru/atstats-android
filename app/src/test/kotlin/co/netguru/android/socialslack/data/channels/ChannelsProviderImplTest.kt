package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.channels.model.*
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.mockito.Mockito.*
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers

@Suppress("IllegalIdentifier")
class ChannelsProviderImplTest {

    companion object {
        @JvmStatic
        private val LATEST = 1000F
        @JvmStatic
        private val USER = "user"

        private val SUCCESSFUL_FILE_OBJECT = FileUploadResponse(true, "",
                FileObject("" ,"", "", ""))

        private val FAILED_FILE_OBJECT = FileUploadResponse(false, "no_content",
                FileObject("" ,"", "", ""))
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsApi: ChannelsApi = mock(ChannelsApi::class.java)

    lateinit var channelsProvider: ChannelsProvider

    @Before
    fun setUp() {
        val channelList = ChannelList(true, listOf())
        val channelHistory = ChannelHistory(true, LATEST, listOf(ChannelMessages(ChannelMessages.MESSAGE_TYPE, LATEST, USER, USER)), false)
        whenever(channelsApi.getChannelsList()).thenReturn(Single.just(channelList))
        whenever(channelsApi.getChannelsHistory(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyBoolean(), ArgumentMatchers.anyFloat(),
                ArgumentMatchers.anyFloat(), ArgumentMatchers.anyBoolean()))
                .thenReturn(Single.just(channelHistory))

        channelsProvider = ChannelsProviderImpl(channelsApi)
    }

    @Test
    fun `should get channels list from api when getting channels list`() {
        //given
        val testObserver = TestObserver<List<Channel>>()
        //when
        channelsProvider.getChannelsList().subscribe(testObserver)
        //then
        verify(channelsApi).getChannelsList()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should upload file to api when uploading file to channel`() {
        //given
        val testObserver = TestObserver<Boolean>()
        whenever(channelsApi.uploadFileToChannel(anyString(), anyObject())).thenReturn(Single.just(SUCCESSFUL_FILE_OBJECT))
        //when
        channelsProvider.uploadFileToChannel("channel", byteArrayOf()).subscribe(testObserver)
        //then
        verify(channelsApi).uploadFileToChannel(anyString(), anyObject())
        testObserver.assertNoErrors()
    }

    @Test
    fun `should return error when uploading file to channel not successful`() {
        //given
        val testObserver = TestObserver<Boolean>()
        whenever(channelsApi.uploadFileToChannel(anyString(), anyObject())).thenReturn(Single.just(FAILED_FILE_OBJECT))
        //when
        channelsProvider.uploadFileToChannel("channel", byteArrayOf()).subscribe(testObserver)
        //then
        testObserver.assertError(Throwable::class.java)
    }
}