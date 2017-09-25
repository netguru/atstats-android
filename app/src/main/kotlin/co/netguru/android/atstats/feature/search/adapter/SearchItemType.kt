package co.netguru.android.atstats.feature.search.adapter

enum class SearchItemType(val position: Int) {

    USERS(0),
    CHANNELS(1);

    companion object {
        fun getItemTypeForPosition(position: Int) : SearchItemType {
            values().filter { it.position == position }
                    .forEach { return it }

            throw IllegalStateException("There is no SearchItemType for position $position")
        }
    }
}