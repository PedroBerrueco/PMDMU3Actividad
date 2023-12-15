package com.pberrueco.pmdmu3actividad

import android.app.Application
import androidx.room.Room

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