package com.example.sortandfilter.di

import android.content.Context
import androidx.room.Room
import com.example.sortandfilter.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "user_db").build()

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.itemDao()
}