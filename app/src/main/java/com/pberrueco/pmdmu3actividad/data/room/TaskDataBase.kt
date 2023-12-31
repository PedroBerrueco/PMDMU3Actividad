package com.pberrueco.pmdmu3actividad.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tareas::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun tareaDao(): TareasDAO
}