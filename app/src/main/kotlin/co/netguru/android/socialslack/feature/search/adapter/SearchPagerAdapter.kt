package co.netguru.android.socialslack.feature.search.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import co.netguru.android.socialslack.feature.search.SearchFragment

class SearchPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = when (SearchItemType.getItemTypeForPosition(position)) {
        SearchItemType.USERS -> SearchFragment.newInstance(SearchItemType.USERS)
        SearchItemType.CHANNELS -> SearchFragment.newInstance(SearchItemType.CHANNELS)
    }

    override fun getCount() = SearchItemType.values().size

    override fun getPageTitle(position: Int): CharSequence = SearchItemType.getItemTypeForPosition(position).name
}