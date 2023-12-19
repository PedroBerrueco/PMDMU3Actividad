package com.pberrueco.pmdmu3actividad.util

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pberrueco.pmdmu3actividad.data.room.Tareas
import com.pberrueco.pmdmu3actividad.databinding.ViewTareaItemBinding

interface TareasAdapterListener {
    fun onTareaClicked(tarea: Tareas)
    fun onEliminarTarea(tarea: Tareas)
    fun onModificarTarea(tarea: Tareas)
    fun mostrarOpciones(tarea: Tareas)
}

class TareasAdapter(private var tareas: List<Tareas>, val listener: TareasAdapterListener) :
    RecyclerView.Adapter<TareasAdapter.TareaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val binding = ViewTareaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TareaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = tareas[position]
        holder.bind(tarea)
    }

    override fun getItemCount(): Int {
        return tareas.size
    }

    fun updateData(newData: List<Tareas>) {
        tareas = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewTareaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tarea: Tareas) {
            binding.tvTittle.text = tarea.tittle
            binding.tvDescription.text = tarea.description

        }
    }

    inner class TareaViewHolder(val binding: ViewTareaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tarea: Tareas) {
            binding.tvTittle.text = tarea.tittle
            binding.tvDescription.text = tarea.description

            // Agregar clics para eliminar y modificar
            binding.root.setOnClickListener { listener.onTareaClicked(tarea) }
            binding.root.setOnLongClickListener {
                mostrarOpciones(tarea)
                true
            }
        }

        fun mostrarOpciones(tarea: Tareas) {
            val opciones = arrayOf("Eliminar", "Modificar")

            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Opciones de tarea")
                .setItems(opciones) { _, which ->
                    when (which) {
                        0 -> listener.onEliminarTarea(tarea) // Llamar a la interfaz para eliminar
                        1 -> listener.onModificarTarea(tarea) // Llamar a la interfaz para modificar
                    }
                }

            builder.create().show()
        }
    }
}
