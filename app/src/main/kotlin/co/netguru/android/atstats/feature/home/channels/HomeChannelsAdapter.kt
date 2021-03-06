package co.netguru.android.atstats.feature.home.channels

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption

class HomeChannelsAdapter(private val filter: ChannelsFilterOption) : RecyclerView.Adapter<HomeChannelsViewHolder>() {

    val channels: MutableList<ChannelStatistics> = mutableListOf()

    override fun getItemCount(): Int {
        return channels.size
    }

    override fun onBindViewHolder(holder: HomeChannelsViewHolder, position: Int) {
        holder.bind(channels[position], filter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChannelsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel_home, parent, false)
        return HomeChannelsViewHolder(view)
    }

    fun addChannels(channels: List<ChannelStatistics>) {
        this.channels.clear()
        this.channels.addAll(channels)
        notifyDataSetChanged()
    }
}