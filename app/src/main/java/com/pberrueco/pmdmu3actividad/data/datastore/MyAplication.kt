package com.pberrueco.pmdmu3actividad.data.datastore

import android.app.Application
import androidx.room.Room
import com.pberrueco.pmdmu3actividad.data.room.TaskDataBase

class MyAplication : Application() {

    lateinit var room: TaskDataBase

    override fun onCreate() {
        super.onCreate()
        room = Room.databaseBuilder(
            applicationContext,
            TaskDataBase::class.java,
            "MyDataBase"
        ).build()
    }
}