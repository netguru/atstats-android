package co.netguru.android.socialslack.data.session

import co.netguru.android.socialslack.data.session.model.Token
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

interface LoginApi {

    @GET("/api/oauth.access")
    fun requestToken(@Query("client_id") clientId: String, @Query("client_secret") clientSecret: String,
                     @Query("code") code: String): Single<Token>
}