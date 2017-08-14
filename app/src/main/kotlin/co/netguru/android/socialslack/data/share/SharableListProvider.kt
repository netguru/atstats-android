package co.netguru.android.socialslack.data.share

object SharableListProvider {

    private const val SHARABLE_ITEM_COUNT = 3

    fun getSharableList(selectedItemPosition: Int, itemList: List<Sharable>): List<Sharable> {
        val selectedItem = itemList[selectedItemPosition]
        val sharableList = itemList.take(SHARABLE_ITEM_COUNT)

        //if our item current position is lower or equal than last in list we should check if sharable list contains it
        if (selectedItem.currentPositionInList() <= sharableList.last().currentPositionInList()) {
            addSelectedItemToProperPositionInSharableList(selectedItem, sharableList.toMutableList())
        }

        //if we won't remove anything from the list, it can contain more than SHARABLE_ITEM_COUNT objects
        return sharableList.take(SHARABLE_ITEM_COUNT)
    }

    private fun addSelectedItemToProperPositionInSharableList(selectedItem: Sharable, itemList: MutableList<Sharable>) {
        //selected item can be inside sharable list, but it's position can be wrong so we have to remove it from list
        removeSelectedSharableFromList(selectedItem.id(), itemList)
        // and then add to proper place
        addSelectedItemToProperPlace(selectedItem, itemList)
    }

    private fun removeSelectedSharableFromList(selectedItemId: String, sharableList: MutableList<Sharable>) {
        val itemToRemove = sharableList.find { it.id() == selectedItemId }
        sharableList.remove(itemToRemove)
    }

    private fun addSelectedItemToProperPlace(sharable: Sharable, sharableList: MutableList<Sharable>) {
        for (i in 0.until(sharableList.size)) {
            if (sharableList[i].currentPositionInList() >= sharable.currentPositionInList()) {
                sharableList.add(i, sharable)
                break
            }
        }
    }
}