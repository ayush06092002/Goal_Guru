package com.who.goalguru.di

import android.content.Context
import androidx.room.Room
import com.who.goalguru.data.ToDoDao
import com.who.goalguru.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun providesToDoDao(database: ToDoDatabase): ToDoDao {
        return database.toDoDao()
    }

    @Singleton
    @Provides
    fun providesToDoDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(context, ToDoDatabase::class.java, "todo_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}