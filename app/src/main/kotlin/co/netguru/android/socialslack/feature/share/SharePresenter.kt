package co.netguru.android.socialslack.feature.share

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class SharePresenter @Inject constructor(private val channelsController: ChannelsController)
    : MvpNullObjectBasePresenter<ShareContract.View>(),
        ShareContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun onSendButtonClick(screenShotByteArray: ByteArray) {
        compositeDisposable += channelsController.uploadFileToChannel(
                "slack-social-test", screenShotByteArray)
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribeBy(
                        onComplete = {
                            Timber.d("Screenshot sent")
                            view.showShareConfirmationDialog()
                        },
                        onError = {
                            Timber.e(it, "Error while uploading screenshot to server")
                        })
    }
}