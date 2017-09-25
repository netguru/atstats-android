package co.netguru.android.atstats.feature.search

import co.netguru.android.atstats.RxSchedulersOverrideRule
import co.netguru.android.atstats.TestHelper.anyObject
import co.netguru.android.atstats.TestHelper.whenever
import co.netguru.android.atstats.data.channels.ChannelsDao
import co.netguru.android.atstats.data.user.UsersController
import co.netguru.android.atstats.data.user.model.User
import co.netguru.android.atstats.data.user.model.UserProfile
import co.netguru.android.atstats.feature.search.adapter.SearchItemType
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyString

@Suppress("IllegalIdentifier")
class SearchPresenterTest {

    companion object {
        private val USER_PROFILE = UserProfile("", "", "", "", "", "", "", "", "", "")
        private val USER = User("1", "User", "User", "User", "User", false, USER_PROFILE)
        private val DELETED_USER = User("1", "User", "User", "User", "User", true, USER_PROFILE)
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val channelsDao = mock<ChannelsDao>()
    val usersController = mock<UsersController>()
    val view = mock<SearchContract.View>()

    lateinit var searchPresenter: SearchPresenter

    @Before
    fun setUp() {
        whenever(usersController.getUsersList()).thenReturn(Single.just(listOf()))
        whenever(channelsDao.getAllChannels()).thenReturn(Single.just(listOf()))
        searchPresenter = SearchPresenter(channelsDao, usersController)
        searchPresenter.attachView(view)
    }

    @Test
    fun `should init users search view when search item users received and getting users successful`() {
        //when
        searchPresenter.searchItemReceived(SearchItemType.USERS)
        //then
        verify(view).initUsersSearchView(anyObject())
    }

    @Test
    fun `should show error message when search item users received and getting users failed`() {
        //given
        whenever(usersController.getUsersList()).thenReturn(Single.error(Throwable()))
        //when
        searchPresenter.searchItemReceived(SearchItemType.USERS)
        //then
        verify(view).showError()
    }

    @Test
    fun `should skip deleted users when init users search view`() {
        //given
        whenever(usersController.getUsersList()).thenReturn(Single.just(listOf(USER, DELETED_USER)))
        //when
        searchPresenter.searchItemReceived(SearchItemType.USERS)
        //then
        verify(view).initUsersSearchView(listOf(USER))
    }

    @Test
    fun `should show progress bar when search item users received`() {
        //when
        searchPresenter.searchItemReceived(SearchItemType.USERS)
        //then
        verify(view).showProgressBar()
    }

    @Test
    fun `should hide progress bar when search item users received and getting users successful`() {
        //when
        searchPresenter.searchItemReceived(SearchItemType.USERS)
        //then
        verify(view).hideProgressBar()
    }

    @Test
    fun `should hide progress bar when search item users received and getting users failed`() {
        //given
        whenever(channelsDao.getAllChannels()).thenReturn(Single.error(Throwable()))
        //when
        searchPresenter.searchItemReceived(SearchItemType.USERS)
        //then
        verify(view).hideProgressBar()
    }

    @Test
    fun `should init channel search view when search item channels received and getting channels successful`() {
        //when
        searchPresenter.searchItemReceived(SearchItemType.CHANNELS)
        //then
        verify(view).initChannelSearchView(anyObject())
    }

    @Test
    fun `should show error message when search item channels received and getting channels failed`() {
        //given
        whenever(channelsDao.getAllChannels()).thenReturn(Single.error(Throwable()))
        //when
        searchPresenter.searchItemReceived(SearchItemType.CHANNELS)
        //then
        verify(view).showError()
    }

    @Test
    fun `should show progress bar when search item channels received`() {
        //when
        searchPresenter.searchItemReceived(SearchItemType.CHANNELS)
        //then
        verify(view).showProgressBar()
    }

    @Test
    fun `should hide progress bar when search item channels received and getting channels successful`() {
        //when
        searchPresenter.searchItemReceived(SearchItemType.CHANNELS)
        //then
        verify(view).hideProgressBar()
    }

    @Test
    fun `should hide progress bar when search item channels received and getting channels failed`() {
        //given
        whenever(channelsDao.getAllChannels()).thenReturn(Single.error(Throwable()))
        //when
        searchPresenter.searchItemReceived(SearchItemType.CHANNELS)
        //then
        verify(view).hideProgressBar()
    }

    @Test
    fun `should filter users list when search query received and search item type equals users`() {
        //given
        searchPresenter.searchItemReceived(SearchItemType.USERS)
        //when
        searchPresenter.searchQueryReceived("")
        //then
        view.filterUsersList(anyString())
    }

    @Test
    fun `should filter users list when search query received and search item type equals channels`() {
        //given
        searchPresenter.searchItemReceived(SearchItemType.CHANNELS)
        //when
        searchPresenter.searchQueryReceived("")
        //then
        view.filterChannelsList(anyString())
    }

    @After
    fun tearDown() {
        searchPresenter.detachView(false)
    }
}