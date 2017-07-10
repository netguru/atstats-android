package co.netguru.android.socialslack.feature.channels.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import java.util.*

class ChannelsAdapter : RecyclerView.Adapter<ChannelsViewHolder>() {

    var channelsList: MutableList<Int> = Collections.emptyList()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChannelsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_channels, parent, false)
        return ChannelsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder?, position: Int) {
        holder?.bind()
    }

    override fun getItemCount() = channelsList.size
}