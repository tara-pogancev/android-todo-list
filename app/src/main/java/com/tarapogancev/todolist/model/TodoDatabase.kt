package com.tarapogancev.todolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tarapogancev.todolist.dao.TodoTaskDao
import java.time.Instant

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoTaskDao(): TodoTaskDao

    companion object {
        @Volatile
        private var INSTANCE : TodoDatabase? = null

        fun getDatabase(context: Context) : TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }
}