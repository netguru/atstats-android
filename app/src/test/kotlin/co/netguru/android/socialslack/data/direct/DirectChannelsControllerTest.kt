package co.netguru.android.socialslack.data.direct

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.direct.model.*
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString

@Suppress("IllegalIdentifier")
class DirectChannelsControllerTest {

    companion object {
        private const val CHANNEL = "channel"
        private const val LATEST = "1000"
        private const val CREATED = 0L
        private const val USER = "UU22233"
        private const val USER_WRITES_TO_US_MOST = "user1"
        private const val USER_WE_WRITE_MOST = "user2"
        private const val USER_WE_TALK_THE_MOST = "user3"
        private const val RANDOM_USER = "user4"
        //
        private val DIRECT_CHANNEL_1 = DirectChannel("1", USER_WRITES_TO_US_MOST, CREATED)
        private val DIRECT_CHANNEL_2 = DirectChannel("2", USER_WE_WRITE_MOST, CREATED)
        private val DIRECT_CHANNEL_3 = DirectChannel("3", USER_WE_TALK_THE_MOST, CREATED)
        private val DIRECT_CHANNEL_4 = DirectChannel("4", RANDOM_USER, CREATED)
        //
        private val MESSAGE_1 = DirectMessage(USER_WE_TALK_THE_MOST, "", LATEST)
        private val MESSAGE_2 = DirectMessage(USER, "", LATEST)
        private val MESSAGE_3 = DirectMessage(USER_WE_TALK_THE_MOST, "", LATEST)
        private val MESSAGE_4 = DirectMessage(USER, "", LATEST)
        private val MESSAGE_5 = DirectMessage(USER_WE_TALK_THE_MOST, "", LATEST)
        //
        private val STATISTICS_USER_WRITE_TO_US_MOST = DirectChannelStatistics("1", USER_WRITES_TO_US_MOST, 1, 4)
        private val STATISTICS_USER_WE_WRITE_MOST = DirectChannelStatistics("2", USER_WE_WRITE_MOST, 4, 1)
        private val STATISTICS_USER_WE_TALK_MORE = DirectChannelStatistics("3", USER_WE_WRITE_MOST, 3, 3)
        private val STATISTICS_USER_RANDOM = DirectChannelStatistics("4", RANDOM_USER, 2, 2)
        //
        private val LIST_CHANNELS = listOf(DIRECT_CHANNEL_1, DIRECT_CHANNEL_2, DIRECT_CHANNEL_3, DIRECT_CHANNEL_4)
        private val DIRECT_CHANNEL_LIST = DirectChannelList(true, LIST_CHANNELS)
        private val LIST_MESSAGES_USER_WE_TALK_MORE = listOf(MESSAGE_1, MESSAGE_2, MESSAGE_3, MESSAGE_4, MESSAGE_5)
        private val LIST_OF_STATISTICS = listOf(STATISTICS_USER_WRITE_TO_US_MOST, STATISTICS_USER_WE_WRITE_MOST, STATISTICS_USER_WE_TALK_MORE, STATISTICS_USER_RANDOM)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val directChannelsApi = mock<DirectChannnelsApi>()

    val directChannelsDao = mock<DirectChannelsDao>()

    lateinit var directChannelsController: DirectChannelsController

    @Before
    fun setUp() {
        directChannelsController = DirectChannelsController(directChannelsApi, directChannelsDao)
    }

    @Test
    fun `should get the list of direct channels when getting direct channel list`() {
        // given
        whenever(directChannelsApi.getDirectMessagesList()).thenReturn(Single.just(DIRECT_CHANNEL_LIST))
        //when
        val testObserver = directChannelsController.getDirectChannelsList().test()
        //then
        verify(directChannelsApi).getDirectMessagesList()
        testObserver.assertNoErrors()
    }

    @Test
    fun `should show error when getting error from direct channel list`() {
        // given
        var throwable = Throwable()
        whenever(directChannelsApi.getDirectMessagesList()).thenReturn(Single.error(throwable))
        // when
        val testObserver = directChannelsController.getDirectChannelsList().test()
        // then
        verify(directChannelsApi).getDirectMessagesList()
        testObserver.assertError(throwable)
    }

    @Test
    fun `should count and store direct messages when messages are fetch`() {
        // given
        whenever(directChannelsApi.getDirectMessagesWithUser(anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(Single.just(DirectChannelsHistory(true, LATEST, LIST_MESSAGES_USER_WE_TALK_MORE, false)))
        // when
        val testObserver = directChannelsController.countDirectChannelStatistics(CHANNEL, USER_WE_TALK_THE_MOST).test()
        // then
        var arg: ArgumentCaptor<DirectChannelStatistics> = ArgumentCaptor.forClass(DirectChannelStatistics::class.java)
        arg?.apply {
            verify(directChannelsDao).insertDirectChannel(com.nhaarman.mockito_kotlin.capture(arg))
            assertEquals(value.messagesFromOtherUser, 3)
            assertEquals(value.messagesFromUs, 2)
        }
        testObserver.assertNoErrors()
    }

    @Test
    fun `should sort the list correctly when request sort user writes to us most`() {
        // when
        val testObserver = directChannelsController.sortUserThatWritesToUsMost(LIST_OF_STATISTICS).test()
        // then
        testObserver.assertValue(listOf(STATISTICS_USER_WRITE_TO_US_MOST, STATISTICS_USER_WE_TALK_MORE, STATISTICS_USER_RANDOM, STATISTICS_USER_WE_WRITE_MOST))
    }

    @Test
    fun `should sort the list correctly when request sort user we write most`() {
        // when
        val testObserver = directChannelsController.sortUserWeWriteMost(LIST_OF_STATISTICS).test()
        // then
        testObserver.assertValue(listOf(STATISTICS_USER_WE_WRITE_MOST, STATISTICS_USER_WE_TALK_MORE, STATISTICS_USER_RANDOM, STATISTICS_USER_WRITE_TO_US_MOST))
    }

    @Test
    fun `should sort the list correctly when request sort user we talk most`() {
        // when
        val testObserver = directChannelsController.sortUserWeTalkTheMost(LIST_OF_STATISTICS).test()
        // then
        testObserver.assertValue(listOf(STATISTICS_USER_WE_TALK_MORE, STATISTICS_USER_WRITE_TO_US_MOST, STATISTICS_USER_WE_WRITE_MOST, STATISTICS_USER_RANDOM))
    }
}