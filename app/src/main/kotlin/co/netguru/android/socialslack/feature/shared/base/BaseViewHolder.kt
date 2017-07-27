package co.netguru.android.socialslack.feature.shared.base

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T)
}