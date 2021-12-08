package com.example.contactbook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactbook.data.daos.ContactDao
import com.example.contactbook.data.entities.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun contactDao() : ContactDao
    companion object{
        @Volatile
        private var INSTANCE: com.example.contactbook.data.ApplicationDatabase? = null

        fun getDatabase(context : Context) : com.example.contactbook.data.ApplicationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "applicationDatabase"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}