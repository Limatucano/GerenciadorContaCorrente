package com.matheus.gerenciadorcontacorrente.data.localstore

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(TypeConverterUtil::class)
@androidx.room.Database(entities = [User::class, Actions::class], version = 6, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun userDAO() : UserDAO
    abstract fun actionsDAO() : ActionsDAO

    companion object{
        @Volatile
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context) : Database?{
            val tempInstance = INSTANCE
            if(INSTANCE != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "gerenciador_conta_corrente"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}