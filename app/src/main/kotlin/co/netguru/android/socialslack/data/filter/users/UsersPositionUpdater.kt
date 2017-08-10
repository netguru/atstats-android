package co.netguru.android.socialslack.data.filter.users

import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic

object UsersPositionUpdater {

    fun updateUsersPositionInList(usersList: List<UserStatistic>, usersFilterOption: UsersFilterOption) {
        if (usersList.isEmpty()) {
            return
        }
        var currentPositionInList = 1
        usersList[0].currentPositionInList = currentPositionInList
        for (i in 1.until(usersList.size)) {
            if (compareUsersDependsOnFilterOption(usersFilterOption, usersList[i], usersList[i - 1])) {
                usersList[i].currentPositionInList = currentPositionInList
            } else {
                usersList[i].currentPositionInList = ++currentPositionInList
            }
        }
    }

    private fun compareUsersDependsOnFilterOption(filterOption: UsersFilterOption, firstUser: UserStatistic,
                                                  secondUser: UserStatistic) = when (filterOption) {
        UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST -> firstUser.sentMessages == secondUser.sentMessages
        UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST -> firstUser.receivedMessages == secondUser.receivedMessages
        UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST -> firstUser.totalMessages == secondUser.totalMessages
    }
}