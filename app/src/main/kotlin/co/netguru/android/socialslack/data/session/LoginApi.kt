package co.netguru.android.socialslack.data.session

import co.netguru.android.socialslack.data.session.model.Token
import co.netguru.android.socialslack.data.session.model.TokenCheck
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApi {

    @GET("/api/oauth.access")
    fun requestToken(@Query("client_id") clientId: String, @Query("client_secret") clientSecret: String,
                     @Query("code") code: String): Single<Token>

    @GET("api/auth.test")
    fun checkToken(): Single<TokenCheck>

    //TODO 06.07.2017 Should be called when userId wants to logout
    @GET("api/auth.revoke")
    fun revokeToken() : Completable
}