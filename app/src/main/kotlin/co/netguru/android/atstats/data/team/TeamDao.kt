package co.netguru.android.atstats.data.team

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import co.netguru.android.atstats.data.team.model.Team
import io.reactivex.Single

@Dao
interface TeamDao {

    @Query("SELECT * FROM team")
    fun getTeam(): Single<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: Team)

    @Query("DELETE FROM team")
    fun deleteAllTeam()
}