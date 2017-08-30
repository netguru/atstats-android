package co.netguru.android.socialslack.feature.search.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import co.netguru.android.socialslack.feature.search.SearchFragment
import android.util.SparseArray
import android.view.ViewGroup

class SearchPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val activeSearchFragments: SparseArray<SearchFragment> = SparseArray(SearchItemType.values().size)

    override fun getItem(position: Int) = when (SearchItemType.getItemTypeForPosition(position)) {
        SearchItemType.USERS -> SearchFragment.newInstance(SearchItemType.USERS)
        SearchItemType.CHANNELS -> SearchFragment.newInstance(SearchItemType.CHANNELS)
    }

    override fun getCount() = SearchItemType.values().size

    override fun getPageTitle(position: Int): CharSequence = SearchItemType.getItemTypeForPosition(position).name

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as SearchFragment
        activeSearchFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        activeSearchFragments.delete(position)
        super.destroyItem(container, position, obj)
    }

    internal fun refreshFragment(position: Int, query: String) {
        activeSearchFragments.get(position).refreshData(query)
    }
}