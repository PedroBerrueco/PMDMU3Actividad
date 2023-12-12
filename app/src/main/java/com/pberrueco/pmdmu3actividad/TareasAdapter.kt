package com.pberrueco.pmdmu3actividad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pberrueco.pmdmu3actividad.databinding.ViewTareaItemBinding


//ADAPTER DE LA RECICLERVIEW
interface TareaClickedListener {
    fun onTareaCliked(tarea: Tarea)
}

class TareasAdapter(val tareas: List<Tarea>, val tareaClickedListener: TareaClickedListener):
    RecyclerView.Adapter<TareasAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Crear nueva vista
        val binding = ViewTareaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //ACtualizar Nueva Vista
        val tarea = tareas[position]
        holder.bind(tarea)
        holder.itemView.setOnClickListener {tareaClickedListener.onTareaCliked(tarea)}
    }

    override fun getItemCount(): Int {
        //Contador de elementos del adapter
        return tareas.size
    }


    class ViewHolder(val binding: ViewTareaItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(tarea:Tarea){
            binding.tvTittle.text = tarea.tittle
            binding.tvDescription.text = tarea.description
        //Si queremos cargar una imagen de internet en una imageView del Item.
        //Glide.with(binding.root.context).load(tarea.description).into(binding.tvDescription)
        }
    }
}