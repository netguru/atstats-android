package co.netguru.android.atstats.data.team

import co.netguru.android.atstats.app.scope.UserScope
import co.netguru.android.atstats.data.team.model.Team
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

@UserScope
class TeamController @Inject constructor(private val teamApi: TeamApi,
                                         private val teamDao: TeamDao) {

    private fun getTeamInfo(): Single<Team> =
            teamApi.getTeamInfo()
                    .map { it.team }

    fun fetchTeamInfo(): Completable = getTeamInfo()
            .doOnSuccess { teamDao.insertTeam(it) }
            .toCompletable()
}