package co.netguru.android.atstats.feature.shared.base

import android.support.v7.widget.RecyclerView
import android.view.View
import co.netguru.android.atstats.data.filter.model.Filter

abstract class BaseViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T, filter: Filter)
}