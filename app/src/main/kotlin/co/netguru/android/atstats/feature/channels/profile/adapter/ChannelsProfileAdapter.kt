package co.netguru.android.atstats.feature.channels.profile.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption


internal class ChannelsProfileAdapter(private val onChannelsShareButtonClickListener: OnChannelsShareButtonClickListener)
    : RecyclerView.Adapter<ChannelsProfileViewHolder>(), ChannelsProfileViewHolder.OnShareButtonClickListener {

    private val channelsStatisticsList: MutableList<ChannelStatistics> = mutableListOf()

    private lateinit var selectedFilterOption: ChannelsFilterOption

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChannelsProfileViewHolder(parent, this)

    override fun onBindViewHolder(holder: ChannelsProfileViewHolder, position: Int) {
        holder.bind(channelsStatisticsList[position], selectedFilterOption)
    }

    override fun getItemCount() = channelsStatisticsList.size

    override fun onShareButtonClicked(itemPosition: Int) {
        onChannelsShareButtonClickListener.onShareButtonClick(itemPosition, channelsStatisticsList)
    }

    internal fun addChannelsStatistics(channelsStatisticsList: List<ChannelStatistics>, filterOption: ChannelsFilterOption) {
        this.selectedFilterOption = filterOption
        this.channelsStatisticsList.clear()
        this.channelsStatisticsList.addAll(channelsStatisticsList)
        notifyDataSetChanged()
    }

    interface OnChannelsShareButtonClickListener {
        fun onShareButtonClick(clickedChannelPosition: Int, channelList: List<ChannelStatistics>)
    }
}