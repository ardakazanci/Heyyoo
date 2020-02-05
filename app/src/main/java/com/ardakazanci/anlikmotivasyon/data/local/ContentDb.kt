package com.ardakazanci.anlikmotivasyon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardakazanci.anlikmotivasyon.data.model.Doc

@Database(
    entities = [Doc::class],
    version = 1,
    exportSchema = false
)
abstract class ContentDb : RoomDatabase() {

    companion object {
        fun create(context: Context): ContentDb {

            val databaseBuilder =
                Room.databaseBuilder(context, ContentDb::class.java, "contents.db")
            return databaseBuilder.build()

        }
    }

    abstract fun contentDao(): ContentDao

}