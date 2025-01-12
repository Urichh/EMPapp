package com.example.aplikacijazanamizneigre.data.dao

import androidx.room.*
import com.example.aplikacijazanamizneigre.data.models.SeznamZelja
import kotlinx.coroutines.flow.Flow

@Dao
interface SeznamZeljaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun dodajZeljo(entry: SeznamZelja)

    @Query("SELECT * FROM seznam_zelja")
    fun getZelje(): Flow<List<SeznamZelja>>

    @Query("SELECT * FROM seznam_zelja WHERE idIgre = :idIgre")
    fun getZeljaById(idIgre: Int): Flow<List<SeznamZelja>>

    @Delete
    suspend fun izbrisiZeljo(entry: SeznamZelja)

    @Query("DELETE FROM seznam_zelja")
    suspend fun izprazniZelje()
}
