package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class ChannelProfilePresenter @Inject constructor(private val channelsDao: ChannelsDao) :
        MvpNullObjectBasePresenter<ChannelProfileContract.View>(), ChannelProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getChannelInfo(ChannelId: String) {

        view.showLoadingView()

        compositeDisposable += channelsDao.getChannelById(ChannelId)
                .compose(RxTransformers.applySingleIoSchedulers())
                .doAfterTerminate { view.hideLoadingView() }
                .subscribeBy(
                        onSuccess = this::showCount,
                        onError = {
                            Timber.e(it, "Couldn't find the channel")
                            view.showError()
                        }
                )
    }

    private fun showCount(channelsStatistics: ChannelStatistics) {
        view.showChannelInfo(channelsStatistics.messageCount, channelsStatistics.hereCount, channelsStatistics.mentionsCount)
    }

    override fun onShareButtonClick() {
        view.showShareDialogFragment()
    }

    override fun searchButtonClicked() {
        view.showSearchView()
    }
}