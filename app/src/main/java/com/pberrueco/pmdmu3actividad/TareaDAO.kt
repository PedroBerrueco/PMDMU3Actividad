package com.pberrueco.pmdmu3actividad

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TareasDAO {

    //Para guardar datos nuevos en la lista
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(tareas: Tareas)


    @Insert
    suspend fun saveManyTask(tareas: List<Tareas>)

    @Update
    suspend fun updateTask(tareas: Tareas)

    @Query("SELECT * FROM tareas")
    fun getAllTask(): List<Tareas>

    @Query("SELECT * FROM tareas WHERE id=:listId")
    suspend fun getOneTask(listId: Int): Tareas

    @Delete
    suspend fun deleteTask(tareas: Tareas)

}
