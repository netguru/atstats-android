package co.netguru.android.socialslack.data.team.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "team")
data class Team(@PrimaryKey val id: String,
                val name: String,
                val domain: String)