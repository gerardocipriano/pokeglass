package com.example.pokeglass.local.teamlocalservice.teamroomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.daos.TeamDao
import com.example.pokeglass.local.teamlocalservice.teamroomdatabase.entities.TeamEntity

@Database(entities = [TeamEntity::class], version = 1)
abstract class TeamRoomDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TeamRoomDatabase? = null

        fun getDatabase(context: Context): TeamRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TeamRoomDatabase::class.java,
                    "team_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}