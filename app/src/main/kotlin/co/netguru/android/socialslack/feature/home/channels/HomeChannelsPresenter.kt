package co.netguru.android.socialslack.feature.home.channels

import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.channels.ChannelsComparator
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toSingle
import timber.log.Timber
import javax.inject.Inject


class HomeChannelsPresenter @Inject constructor(private val channelsDao: ChannelsDao) :
        MvpNullObjectBasePresenter<HomeChannelsContract.View>(), HomeChannelsContract.Presenter {

    private companion object {
        private const val CHANNELS_SHOWN_IN_STATISTICS = 3
        private const val NUMBER_OF_SUBSCRIBER = 3
    }

    val compositeDisposable = CompositeDisposable()

    override fun attachView(view: HomeChannelsContract.View) {
        super.attachView(view)
        fetchChannels()
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    private fun fetchChannels() {
        val channelStream = channelsDao.getAllChannels()
                .toFlowable()
                .publish()
                .autoConnect(NUMBER_OF_SUBSCRIBER)

        findMostActiveChannels(channelStream)
        findChannelsWeAreMentionTheMost(channelStream)
        findChannelsWeAreMostActive(channelStream)
    }

    private fun findMostActiveChannels(channelStream: Flowable<List<ChannelStatistics>>) {
        compositeDisposable += sortWithFilter(channelStream, ChannelsFilterOption.MOST_ACTIVE_CHANNEL)
                .subscribeBy(
                        onSuccess = view::showMostActiveChannels,
                        onError = { showError(it, "Error finding the most active channels") }
                )
    }

    private fun findChannelsWeAreMentionTheMost(channelStream: Flowable<List<ChannelStatistics>>) {
        compositeDisposable += sortWithFilter(channelStream, ChannelsFilterOption.CHANNEL_WE_ARE_MENTIONED_THE_MOST)
                .subscribeBy(
                        onSuccess = view::showChannelsWeAreMentionTheMost,
                        onError = { showError(it, "Error finding the channel we are mention most") }
                )
    }

    private fun findChannelsWeAreMostActive(channelStream: Flowable<List<ChannelStatistics>>) {
        compositeDisposable += sortWithFilter(channelStream, ChannelsFilterOption.CHANNEL_WE_ARE_MOST_ACTIVE)
                .subscribeBy(
                        onSuccess = view::showChannelsWeAreMostActive,
                        onError = {showError(it, "Error finding the channels we are most active")}
                )
    }

    private fun sortWithFilter(channelStream: Flowable<List<ChannelStatistics>>, filter: ChannelsFilterOption) =
            channelStream
                    .flatMapSingle { this.sortChannelList(it, filter) }
                    .firstOrError()
                    .compose(RxTransformers.applySingleIoSchedulers())


    private fun sortChannelList(channelList: List<ChannelStatistics>, filter: ChannelsFilterOption) =
            Flowable.fromIterable(channelList)
                    .toSortedList(ChannelsComparator.getChannelsComparatorForFilterOption(filter))
                    .map { it.subList(0, CHANNELS_SHOWN_IN_STATISTICS) }

    private fun showError(throwable: Throwable, msg: String) {
        Timber.e(throwable, msg)
        view.showErrorSortingChannels()
    }
}