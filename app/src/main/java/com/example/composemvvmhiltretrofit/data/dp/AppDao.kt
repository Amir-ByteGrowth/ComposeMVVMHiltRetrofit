package com.example.composemvvmhiltretrofit.data.dp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composemvvmhiltretrofit.data.models.MotivationDataEntity
import com.example.composemvvmhiltretrofit.data.models.MotivationDataItem
import com.example.composemvvmhiltretrofit.data.models.Suggestion
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {


    // to get list of qr codes dependent upon viewType either scanned or generated qr

    @Query("SELECT * FROM motiviation_table")
    fun getAllMotivation(): List<MotivationDataItem>

    // below is the insert method for
    // adding a new entry to our database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQrCode(motivationDataEntity: MotivationDataEntity)

//    //to insert suggestions
    @Insert
    suspend fun insertSuggestion(suggestion: Suggestion)

    //to get Suggestions
    @Query("SELECT * FROM suggestions where handle LIKE '%' || :search || '%'")
    suspend fun getSuggestions(search:String): List<Suggestion>

    @Query("SELECT * FROM suggestions order by id des")
     fun getSuggestionsList(): Flow<List<Suggestion>>

}