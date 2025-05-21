package com.example.habitpal.domain.database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.habitpal.domain.enums.Frequency
import com.example.habitpal.domain.models.Habit
import com.example.habitpal.domain.models.HabitGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "habit_db"
            ).addCallback(SeedCallback()).build().also { INSTANCE = it }
        }
    }


    private class SeedCallback() : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                val groupDao = INSTANCE?.groupDao()
                val habitDao = INSTANCE?.habitDao()
                if(groupDao!=null && habitDao!=null){

                    if(groupDao.getAllGroups().isEmpty()){

                        val morningId = groupDao.insertGroup(HabitGroup(name = "Morning"))
                        val eveningId = groupDao.insertGroup(HabitGroup(name = "Evening"))

                        if(habitDao.getAll().isEmpty()){

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
        }
    }



}
