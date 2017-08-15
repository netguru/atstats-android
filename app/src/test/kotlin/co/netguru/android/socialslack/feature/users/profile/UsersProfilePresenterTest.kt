package co.netguru.android.socialslack.feature.users.profile

import co.netguru.android.socialslack.RxSchedulersOverrideRule

import co.netguru.android.socialslack.TestHelper.anyObject
import co.netguru.android.socialslack.TestHelper.whenever
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.profile.UsersProfileController
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class UsersProfilePresenterTest {

    companion object {
        private val USER1 = UserStatistic("1", "", "", "", "", 1, 1, 2, 1, null, 1)
        private val USER2 = UserStatistic("2", "", "", "", "", 2, 2, 4, 1, null, 2)
        private val USER3 = UserStatistic("3", "", "", "", "", 3, 3, 6, 1, null, 3)
        private val USER33 = UserStatistic("4", "", "", "", "", 3, 3, 6, 1, null, 3)
        private val MOCKED_FILTER_OPTION = UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST
    }

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    val usersProfileController = mock<UsersProfileController>()

    lateinit var view: UsersProfileContract.View

    lateinit var usersProfilePresenter: UsersProfilePresenter

    @Before
    fun setUp() {
        whenever(usersProfileController.getUserWithPresence(anyObject())).thenReturn(Flowable.just(USER1))

        view = mock(UsersProfileContract.View::class.java)
        usersProfilePresenter = UsersProfilePresenter(usersProfileController)
        usersProfilePresenter.attachView(view)
    }

    @Test
    fun `should get users presence when preparing view`() {
        //when
        usersProfilePresenter.prepareView(listOf(USER1), 0, MOCKED_FILTER_OPTION)
        //then
        verify(usersProfileController).getUserWithPresence(anyObject())
    }

    @Test
    fun `should init view view when preparing view successful`() {
        //when
        usersProfilePresenter.prepareView(listOf(USER1), 0, MOCKED_FILTER_OPTION)
        //then
        verify(view).initView(anyObject(), anyObject())
    }

    @Test
    fun `should scroll to user position when preparing view successful`() {
        //when
        usersProfilePresenter.prepareView(listOf(USER1), 0, MOCKED_FILTER_OPTION)
        //then
        verify(view).scrollToUserPosition(anyInt())
    }

    @Test
    fun `should show loading view while preparing view`() {
        //when
        usersProfilePresenter.prepareView(listOf(USER1), 0, MOCKED_FILTER_OPTION)
        //then
        verify(view).showLoadingView()
    }

    @Test
    fun `should hide loading view when preparing view successful`() {
        //when
        usersProfilePresenter.prepareView(listOf(USER1), 0, MOCKED_FILTER_OPTION)
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should hide loading view when preparing view failed`() {
        //given
        whenever(usersProfileController.getUserWithPresence(anyObject())).thenReturn(Flowable.error(Throwable()))
        //when
        usersProfilePresenter.prepareView(listOf(USER1), 0, MOCKED_FILTER_OPTION)
        //then
        verify(view).hideLoadingView()
    }

    @Test
    fun `should show selected user with 3 users that we talk the most whe on share button clicked`() {
        //when
        usersProfilePresenter.onShareButtonClicked(0, listOf(USER1, USER2, USER3, USER33))
        //then
        verify(view).showShareView(USER1, listOf(USER1, USER2, USER3))
    }

    @Test
    fun `should change selected user position when other user has the same messages number but higher position`() {
        //when
        usersProfilePresenter.onShareButtonClicked(2, listOf(USER1, USER3, USER33))
        //then
        verify(view).showShareView(USER33, listOf(USER1, USER33, USER3))
    }

    @Test
    fun `should add selected user to list when it's missing and it's position equals last item position`() {
        //when
        usersProfilePresenter.onShareButtonClicked(3, listOf(USER1, USER2, USER3, USER33))
        //then
        verify(view).showShareView(USER33, listOf(USER1, USER2, USER33))
    }

    @Test
    fun `should show share view when on share button clicked and getting sharable list successful`() {
        //when
        usersProfilePresenter.onShareButtonClicked(0, listOf(USER1))
        //then
        verify(view).showShareView(anyObject(), anyObject())
    }

    @After
    fun tearDown() {
        usersProfilePresenter.detachView(false)
    }
}