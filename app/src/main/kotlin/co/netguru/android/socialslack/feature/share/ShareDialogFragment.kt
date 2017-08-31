package co.netguru.android.socialslack.feature.share

import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.util.ScreenShotUtils
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.channels.ChannelsMessagesNumberProvider
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.Filter
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.filter.users.UsersMessagesNumberProvider
import co.netguru.android.socialslack.data.share.Sharable
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.share.adapter.ShareChannelAdapter
import co.netguru.android.socialslack.feature.share.adapter.ShareUserAdapter
import co.netguru.android.socialslack.feature.share.confirmation.ShareConfirmationDialogFragment
import co.netguru.android.socialslack.feature.shared.base.BaseMvpDialogFragment
import co.netguru.android.socialslack.feature.shared.view.DividerItemDecorator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_share.*
import kotlinx.android.synthetic.main.item_channels.view.*
import kotlinx.android.synthetic.main.item_users.view.*

class ShareDialogFragment : BaseMvpDialogFragment<ShareContract.View, ShareContract.Presenter>(),
        ShareContract.View {

    companion object {
        fun <T> newInstance(selectedItem: T, mostActiveItemList: Array<T>, filter: Filter): ShareDialogFragment
                where T : Parcelable, T : Sharable {

            val fragment = ShareDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(SELECTED_ITEM_KEY, selectedItem)
            bundle.putParcelableArray(MOST_ACTIVE_ITEM_LIST_KEY, mostActiveItemList)
            bundle.putString(FILTER_OPTION, filter.filterName())

            fragment.arguments = bundle
            return fragment
        }

        val TAG: String = ShareDialogFragment::class.java.simpleName

        private const val SELECTED_ITEM_KEY = "key:selected_item"
        private const val MOST_ACTIVE_ITEM_LIST_KEY = "key:most_active_item_list"
        private const val FILTER_OPTION = "key:filter_option"
    }

    private val component by lazy {
        App.getUserComponent(context)
                .plusChannelShareComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        shareCloseBtn.setOnClickListener { presenter.onCloseButtonClick() }
        shareSendButton.setOnClickListener {
            presenter.onSendButtonClick(ScreenShotUtils.takeScreenShotByteArray(shareRootView))
        }
        presenter.prepareView(arguments.getParcelable(SELECTED_ITEM_KEY),
                arguments.getParcelableArray(MOST_ACTIVE_ITEM_LIST_KEY).toList(),
                arguments.getString(FILTER_OPTION))
    }

    override fun createPresenter() = component.getPresenter()

    override fun initShareChannelView(channelsList: List<ChannelStatistics>, filterOption: ChannelsFilterOption) {
        val adapter = ShareChannelAdapter(channelsList, filterOption)
        shareRecyclerView.adapter = adapter
    }

    override fun initShareUsersView(userList: List<UserStatistic>, filterOption: UsersFilterOption) {
        val adapter = ShareUserAdapter(userList, filterOption)
        shareRecyclerView.adapter = adapter
    }

    override fun showChannelName(channelName: String) {
        shareNameTextView.text = resources.getString(R.string.share_recon_this, channelName)
        shareAboutSendStatisticsTextView.text = resources.getString(R.string.share_send_statistics_to, channelName)
    }

    override fun showSelectedChannelMostActiveText() {
        shareStatusTextView.text = resources.getString(R.string.share_most_talkative_channel)
    }

    override fun showSelectedChannelTalkMoreText() {
        shareStatusTextView.text = resources.getString(R.string.share_talk_more)
    }

    override fun showSelectedChannelOnLastPosition(channelStatistics: ChannelStatistics, filterOption: ChannelsFilterOption) {
        shareMoreVertImage.visibility = View.VISIBLE
        shareLastChannelContainer.visibility = View.VISIBLE
        showLastChannelData(channelStatistics, filterOption)
    }

    override fun showMentionsNrTitle() {
        shareNrTitleTextView.setText(R.string.number_of_mentions)
    }

    override fun showMessagesNrTitle() {
        shareNrTitleTextView.setText(R.string.number_of_messages)
    }

    override fun showSelectedUserMostActiveText() {
        shareStatusTextView.text = resources.getString(R.string.share_most_talkative_user)
    }

    override fun showSelectedUserTalkMoreText() {
        shareStatusTextView.text = resources.getString(R.string.share_talk_more)
    }

    override fun showSelectedUserOnLastPosition(user: UserStatistic, filterOption: UsersFilterOption) {
        shareMoreVertImage.visibility = View.VISIBLE
        shareLastUserContainer.visibility = View.VISIBLE
        showLastUserData(user, filterOption)
    }

    override fun showShareConfirmationDialog(itemName: String) {
        ShareConfirmationDialogFragment.newInstance(itemName).show(fragmentManager,
                ShareConfirmationDialogFragment.TAG)
        dismiss()
    }

    override fun showLoadingView() {
        shareSendButton.visibility = View.GONE
        shareLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        shareSendButton.visibility = View.VISIBLE
        shareLoadingView.visibility = View.GONE
    }

    override fun dismissView() {
        dismiss()
    }

    override fun showError() {
        Snackbar.make(shareRecyclerView, R.string.error_msg, Snackbar.LENGTH_LONG).show()
    }

    private fun showLastChannelData(channelStatistics: ChannelStatistics, filterOption: ChannelsFilterOption) {
        shareLastChannel.itemChannelsMessagesNrTextView.text = ChannelsMessagesNumberProvider
                .getProperMessagesNumber(filterOption, channelStatistics).toString()

        with(channelStatistics) {
            shareLastChannel.itemChannelsPlaceNrTextView.text = (currentPositionInList.toString() + '.')
            shareLastChannel.itemChannelsNameTextView.text = channelName
        }
    }

    private fun showLastUserData(user: UserStatistic, filterOption: UsersFilterOption) {
        shareLastUser.messagesNrTextView.text = UsersMessagesNumberProvider.getProperMessagesNumber(filterOption, user).toString()
        with(user) {
            shareLastUser.placeNrTextView.text = (currentPositionInList.toString() + '.')
            shareLastUser.userRealNameTextView.text = name
            shareLastUser.usernameTextView.text = (context.getString(R.string.username, username))
            loadUserPhoto(user.avatarUrl)
        }
    }

    private fun loadUserPhoto(avatarUrl: String?) {
        Glide.with(this)
                // TODO 14.08.2017 find a better placeholder
                .load(avatarUrl ?: R.drawable.this_is_totally_a_person)
                .apply(RequestOptions.centerCropTransform()
                        .transform(RoundedCorners(resources.getDimension(R.dimen.item_user_avatar_radius).toInt())))
                .into(shareLastUser.userAvatarImageView)
    }

    private fun initRecyclerView() {
        shareRecyclerView.setHasFixedSize(true)
        shareRecyclerView.addItemDecoration(DividerItemDecorator(
                context,
                DividerItemDecorator.Orientation.VERTICAL_LIST, false))
    }
}