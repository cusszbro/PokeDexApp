package com.luthfirr.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.luthfirr.core.data.source.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase? {
            if (INSTANCE == null) {
                synchronized(PokemonDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, PokemonDatabase::class.java, "pokemon_database").build()

                }
            }
            return INSTANCE
        }
    }
    abstract fun pokemonDao(): PokemonDao
}