package com.deloitte.pokedex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deloitte.pokedex.entity.PokemonRemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<PokemonRemoteKeys>)
    @Query("SELECT * FROM RemoteKeys WHERE name = :name")
    fun remoteKeyByPokemonName(name: String?): PokemonRemoteKeys?
    @Query("DELETE FROM RemoteKeys")
    fun clearRemoteKeys()
}