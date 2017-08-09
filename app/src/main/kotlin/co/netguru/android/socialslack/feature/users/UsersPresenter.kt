package co.netguru.android.socialslack.feature.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.filter.ChannelsComparator
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class UsersPresenter @Inject constructor(private val filterController: FilterController)
    : MvpNullObjectBasePresenter<UsersContract.View>(), UsersContract.Presenter {

    companion object {
        //TODO 04.08.2017 Remove while integrating API
        private const val MOCKED_DATA_SIZE = 20
    }

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun getCurrentFilterOption() {
        compositeDisposable += filterController.getUsersFilterOption()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.changeSelectedFilterOption(it.textResId)
                        },
                        onError = {
                            Timber.e(it, "Error while getting current filter option")
                            view.showFilterOptionError()
                        }
                )
    }

    override fun getUsersData() {
        view.showUsersList(getMockedData())
        view.hideLoadingView()
    }

    override fun onUserClicked(clickedUserPosition: Int) {
        view.showUserDetails(clickedUserPosition)
    }

    override fun filterButtonClicked() {
        view.showFilterView()
    }

    override fun sortRequestReceived(usersList: List<UserStatistic>) {
        view.showLoadingView()
        compositeDisposable += Single.just(usersList)
                .zipWith(filterController.getUsersFilterOption())
                { userList, filterOption -> Pair(userList, filterOption) }
                .flatMap { sortUsersList(it.first, it.second) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .doAfterTerminate { view.hideLoadingView() }
                .subscribeBy(
                        onSuccess = { (userList, filterOption) ->
                            view.showUsersList(userList)
                            view.changeSelectedFilterOption(filterOption.textResId)
                        },
                        onError = {
                            Timber.e(it, "Error while changing users order")
                            view.showFilterOptionError()
                        })
    }

    private fun sortUsersList(usersList: List<UserStatistic>, usersFilterOption: UsersFilterOption
    ): Single<Pair<List<UserStatistic>, UsersFilterOption>> {
        return Observable.fromIterable(usersList)
                .toSortedList(ChannelsComparator.getUsersComparatorForFilterOption(usersFilterOption))
                .doOnSuccess { updateUsersPositionInList(it, usersFilterOption) }
                .map { Pair(it, usersFilterOption) }
    }

    private fun updateUsersPositionInList(usersList: List<UserStatistic>, filterOption: UsersFilterOption) {
        if (usersList.isEmpty()) {
            return
        }
        var currentPositionInList = 1
        usersList[0].currentPositionInList = currentPositionInList
        for (i in 1.until(usersList.size)) {
            if (usersList[i].totalMessages == usersList[i - 1].totalMessages) {
                usersList[i].currentPositionInList = currentPositionInList
            } else {
                usersList[i].currentPositionInList = ++currentPositionInList
            }
        }
    }

    //TODO 04.08.2017 Remove while integrating API
    private fun getMockedData(): List<UserStatistic> {
        val userList = mutableListOf<UserStatistic>()

        for (i in 1..MOCKED_DATA_SIZE) {
            userList += UserStatistic("U2JHH3HAA",
                    "rafal.adasiewicz",
                    "Rafal Adasiewicz",
                    350 - i,
                    50,
                    300 - i,
                    350 - i,
                    7,
                    "https://avatars.slack-edge.com/2016-10-10/89333489734_47e72c65c34236dcff70_192.png",
                    i
            )
        }

        return userList
    }
}