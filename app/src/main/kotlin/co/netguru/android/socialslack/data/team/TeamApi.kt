package co.netguru.android.socialslack.data.team

import co.netguru.android.socialslack.data.team.model.TeamResponse
import io.reactivex.Single
import retrofit2.http.GET


interface TeamApi {

    @GET("api/team.info")
    fun getTeamInfo(): Single<TeamResponse>
}