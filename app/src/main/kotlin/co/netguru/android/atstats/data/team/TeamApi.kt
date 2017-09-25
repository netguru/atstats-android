package co.netguru.android.atstats.data.team

import co.netguru.android.atstats.data.team.model.TeamResponse
import io.reactivex.Single
import retrofit2.http.GET


interface TeamApi {

    @GET("api/team.info")
    fun getTeamInfo(): Single<TeamResponse>
}