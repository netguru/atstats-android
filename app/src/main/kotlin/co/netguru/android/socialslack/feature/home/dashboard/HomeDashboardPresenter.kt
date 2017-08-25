package co.netguru.android.socialslack.feature.home.dashboard

import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.feature.home.dashboard.model.ChannelsCount
import co.netguru.android.socialslack.feature.home.dashboard.model.DirectChannelsCount
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class HomeDashboardPresenter @Inject constructor(private val sessionController: SessionController,
                                                 private val usersController: UsersController,
                                                 private val channelsDao: ChannelsDao,
                                                 private val directChannelsDao: DirectChannelsDao) :
        MvpNullObjectBasePresenter<HomeDashboardContract.View>(), HomeDashboardContract.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun attachView(view: HomeDashboardContract.View) {
        super.attachView(view)
        showProfilePicture()
        fetch()
    }

    private fun showProfilePicture() {
        compositeDisposable += sessionController.getUserSession()
                .flatMap {
                    usersController.getUserInfo(it.userId)
                }
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = { view.showProfile(it.username, it.profile.image512) },
                        onError = {
                            Timber.e(it, "Error while getting the profile details")
                            view.showProfileError()
                        }
                )
    }

    private fun fetch() {
        compositeDisposable += getChannelsCount()
                .zipWith(getDirectChannelsCount().subscribeOn(Schedulers.io()))
                { channelsCount, directChannelsCount -> Pair(channelsCount, directChannelsCount) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = { (channelsCount, directChannelsCount) ->
                            view.showCounts(channelsCount, directChannelsCount)
                        },
                        onError = {
                            Timber.e(it, "Error while counting channels and directChannels statistics")
                            view.showCountError()
                        }
                )
    }

    private fun getChannelsCount(): Single<ChannelsCount> = channelsDao.getAllChannels()
            .observeOn(Schedulers.computation())
            .flattenAsFlowable { it }
            .collect({ ChannelsCount() },
                    { t1: ChannelsCount?, t2: ChannelStatistics -> t1?.accept(t2) })

    private fun getDirectChannelsCount(): Single<DirectChannelsCount> = directChannelsDao.getAllDirectChannels()
            .observeOn(Schedulers.computation())
            .flattenAsFlowable { it }
            .collect({ DirectChannelsCount() },
                    { t1: DirectChannelsCount?, t2: DirectChannelStatistics -> t1?.accept(t2) })
}