package co.netguru.android.socialslack.feature.home.channels

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.user.User
import kotlinx.android.synthetic.main.item_channel.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class HomeChannelsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(channel: Channel) {
        // TODO 11.07.17 remove mock data
        itemView.channelName.text = channel.name
        itemView.channelMessages.text = "234 532"
    }
}