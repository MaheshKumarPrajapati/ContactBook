package com.example.contactbook.di

import android.content.Context
import androidx.room.Room
import com.example.contactbook.data.ApplicationDatabase
import com.example.contactbook.data.daos.ContactDao
import com.example.contactbook.data.repositories.implementations.ContactRepository
import com.example.contactbook.data.services.HashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class Startup {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        ApplicationDatabase::class.java,
        "applicationDatabase"
    ).build()

    @Provides
    fun provideContactDao(database: ApplicationDatabase) = database.contactDao()

    @Provides
    fun provideContactRepository(contactDao: ContactDao) = ContactRepository(contactDao)
}