package com.pberrueco.pmdmu3actividad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pberrueco.pmdmu3actividad.databinding.ViewTareaItemBinding

class TareasAdapter(private var tareas: List<Tareas>, private val onTareaClicked: (Tareas) -> Unit) :
    RecyclerView.Adapter<TareasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewTareaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = tareas[position]
        holder.bind(tarea)
        holder.itemView.setOnClickListener { onTareaClicked(tarea) }
    }

    override fun getItemCount(): Int {
        return tareas.size
    }

    fun updateData(newData: List<Tareas>) {
        tareas = newData
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ViewTareaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tarea: Tareas) {
            binding.tvTittle.text = tarea.tittle
            binding.tvDescription.text = tarea.description
        }
    }
}
