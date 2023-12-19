package com.pberrueco.pmdmu3actividad.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tareas(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tittle: String,
    val description: String
)
