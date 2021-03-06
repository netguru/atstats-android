package co.netguru.android.atstats.feature.users.profile

import co.netguru.android.atstats.app.scope.FragmentScope
import co.netguru.android.atstats.common.util.RxTransformers
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.share.SharableListProvider
import co.netguru.android.atstats.data.user.model.UserStatistic
import co.netguru.android.atstats.data.user.profile.UsersProfileController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class UsersProfilePresenter @Inject constructor(private val usersProfileController: UsersProfileController)
    : MvpNullObjectBasePresenter<UsersProfileContract.View>(), UsersProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun prepareView(userStatisticsList: List<UserStatistic>, currentUserPosition: Int,
                             selectedFilterOption: UsersFilterOption) {
        view.showLoadingView()
        compositeDisposable += Flowable.fromIterable(userStatisticsList)
                .concatMapEager {
                    usersProfileController.getUserWithPresence(it)
                            .subscribeOn(Schedulers.io())
                }
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.initView(it, selectedFilterOption)
                            view.hideLoadingView()
                            view.scrollToUserPosition(currentUserPosition)
                        },
                        onError = {
                            Timber.e(it, "Error while getting user presence")
                            view.hideLoadingView()
                        }
                )
    }

    override fun onShareButtonClicked(clickedItemPosition: Int, usersList: List<UserStatistic>) {
        compositeDisposable += Single.just(usersList)
                .map { SharableListProvider.getSharableList(clickedItemPosition, usersList) }
                .map { it.filterIsInstance(UserStatistic::class.java) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.showShareView(usersList[clickedItemPosition], it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting sharable users list")
                        })
    }

    override fun searchButtonClicked() {
        view.showSearchView()
    }
}