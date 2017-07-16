package co.netguru.android.socialslack.feature.home.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.direct.DirectMessagesController
import co.netguru.android.socialslack.data.direct.model.DirectChannel
import co.netguru.android.socialslack.data.direct.model.DirectChannelWithMessages
import co.netguru.android.socialslack.data.direct.model.Message
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@FragmentScope
class HomeUsersPresenter @Inject constructor(
        private val directMessagesController: DirectMessagesController)
    : MvpNullObjectBasePresenter<HomeUsersContract.View>(), HomeUsersContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    companion object {
        val MAX_MESSAGES_PER_REQUEST = 1000
    }

    override fun attachView(view: HomeUsersContract.View) {
        super.attachView(view)
        fetchMessages()
    }

    private fun fetchMessages() {
        directMessagesController.getDirectMessagesList()
                .compose(RxTransformers.applyFlowableIoSchedulers())
                .flatMapIterable { x -> x }
                .delay(1000, TimeUnit.MILLISECONDS)
                .flatMapSingle { fetchMessagesInConversation(it) }
                .toList()
                .subscribeBy(onSuccess = this::processUserData, onError = { it.printStackTrace() })
    }

    private fun processUserData(channels: List<DirectChannelWithMessages>) {
        findUserWeWriteMost(channels)
        findUserThatWritesToUsTheMost(channels)
        findUserWeTalkTheMost(channels)
    }

    private fun findUserWeWriteMost(channels: List<DirectChannelWithMessages>) {
        // directChannel with most of our messages
    }

    private fun findUserThatWritesToUsTheMost(channels: List<DirectChannelWithMessages>) {
        // directChannel with person that writes to us the most
    }

    private fun findUserWeTalkTheMost(channels: List<DirectChannelWithMessages>) {
        // directChannel with the most messages overall
        channels.toSortedSet(object : Comparator<DirectChannelWithMessages> {

        })
    }

    private fun fetchMessagesInConversation(directChannel: DirectChannel): Single<DirectChannelWithMessages> {
        var latestTimestamp: String? = null
        return Flowable.range(0, Int.MAX_VALUE - 1)
                .concatMap { getMessagesOnIO(directChannel.id, latestTimestamp) }
                .doOnNext { if (it.isNotEmpty()) latestTimestamp = it.last().timeStamp }
                .takeUntil { it.size != MAX_MESSAGES_PER_REQUEST }
                .scan(this::addLists)
                .lastOrError()
                .map { DirectChannelWithMessages(directChannel, it) }
    }

    private fun getMessagesOnIO(inChannel: String, latestTimestamp: String?): Flowable<List<Message>> {
        return directMessagesController.getMessagesInDirectChannel(inChannel,
                latestTimestamp, MAX_MESSAGES_PER_REQUEST)
                .compose(RxTransformers.applyFlowableIoSchedulers())
    }

    private fun addLists(a: List<Message>, b: List<Message>): List<Message> {
        val list: MutableList<Message> = mutableListOf<Message>()
        list.addAll(a)
        list.addAll(b)
        return list.toList()
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

}