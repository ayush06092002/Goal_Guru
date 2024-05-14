package com.who.goalguru.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.who.goalguru.models.ToDo
import com.who.goalguru.utils.Converters

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase(){
    abstract fun toDoDao(): ToDoDao
}