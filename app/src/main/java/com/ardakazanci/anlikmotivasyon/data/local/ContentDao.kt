package com.ardakazanci.anlikmotivasyon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

import androidx.paging.DataSource
import androidx.room.Query
import com.ardakazanci.anlikmotivasyon.data.model.Doc

@Dao
interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(content: List<Doc>)


    @Query("SELECT * FROM Doc")
    fun contents(): DataSource.Factory<Int, Doc>

}