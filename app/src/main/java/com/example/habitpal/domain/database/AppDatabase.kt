package com.example.habitpal.domain.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.habitpal.domain.daos.HabitDao
import com.example.habitpal.domain.daos.HabitGroupDao
import com.example.habitpal.domain.daos.HabitLogDao
import com.example.habitpal.domain.enums.Frequency
import com.example.habitpal.domain.models.*
import com.example.habitpal.domain.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Habit::class, HabitLog::class, HabitGroup::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
    abstract fun habitLogDao(): HabitLogDao
    abstract fun groupDao(): HabitGroupDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "habit_db"
                )
                    .addCallback(SeedCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SeedCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                val database = getDatabase(context)
                val groupDao = database.groupDao()
                val habitDao = database.habitDao()

                val morningId = groupDao.insertGroup(HabitGroup(name = "Morning"))
                val eveningId = groupDao.insertGroup(HabitGroup(name = "Evening"))

                habitDao.insert(
                    Habit(
                        title = "Drink Water",
                        description = "Drink a glass of water after waking up",
                        frequency = Frequency.DAILY,
                        groupId = morningId
                    )
                )

                habitDao.insert(
                    Habit(
                        title = "Read Book",
                        description = "Read 20 minutes before sleep",
                        frequency = Frequency.DAILY,
                        groupId = eveningId
                    )
                )
            }
        }
    }
}
