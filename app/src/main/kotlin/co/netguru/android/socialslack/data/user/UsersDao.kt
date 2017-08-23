package co.netguru.android.socialslack.data.user

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import co.netguru.android.socialslack.data.user.model.UserDB
import io.reactivex.Single

@Dao
interface UsersDao {

    @Query("SELECT * FROM users_table WHERE id = :id")
    fun getUser(id: String): Single<UserDB>

    @Query("SELECT * FROM users_table")
    fun getAllUsers(): Single<List<UserDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserDB)
}