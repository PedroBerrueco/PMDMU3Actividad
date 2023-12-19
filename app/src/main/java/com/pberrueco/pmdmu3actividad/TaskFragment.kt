package com.pberrueco.pmdmu3actividad

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.pberrueco.pmdmu3actividad.databinding.FragmentTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskFragment : Fragment(), TareasAdapterListener {
    private lateinit var _binding: FragmentTaskBinding
    private val binding: FragmentTaskBinding get() = _binding
    var logged = true

    private val tareasAdapter: TareasAdapter by lazy {
        TareasAdapter(listOf(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //REcuperamos usuario y lo pintamos en editText
        val user = arguments?.getString("user")
        binding.etTaskfrag.text = "Hola $user"

        binding.rvTareas.adapter = tareasAdapter
        showMeList()

        binding.fabAdd.setOnClickListener {
            showAddTaskDialog()
        }


        if(!logged){
            gotoRegister()
        }

        binding.imAccount.setOnClickListener {
            val message = "¿Estás seguro de cerrar sesión?"
            ConfirmationDialog(message) {
                // Acciones a realizar si el usuario confirma cerrar sesión
                logged = false
                gotoRegister()
            }
        }



        binding.imDelete.setOnClickListener {
            val user = arguments?.getString("user")
            user?.let { username ->
                val message = "¿Estás seguro de borrar el usuario?"
                ConfirmationDialog(message) {
                    // Acciones a realizar si el usuario confirma borrar usuario
                    deleteUser(username)
                    gotoRegister()
                }
            }
        }

    }

    private fun showMeList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = (requireActivity().application as MyAplication).room.tareaDao()
            val updatedData = dao.getAllTask()

            // Cambiar al hilo principal para actualizar la interfaz de usuario
            lifecycleScope.launch(Dispatchers.Main) {
                // Ahora llama a updateData() en el hilo principal
                tareasAdapter.updateData(updatedData)
            }
        }
    }

    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Nueva Tarea")

        val inputLayout = LinearLayout(requireContext())
        inputLayout.orientation = LinearLayout.VERTICAL

        val inputTitle = EditText(requireContext())
        inputTitle.hint = "Título"
        inputLayout.addView(inputTitle)

        val inputDescription = EditText(requireContext())
        inputDescription.hint = "Descripción"
        inputLayout.addView(inputDescription)

        builder.setView(inputLayout)

        builder.setPositiveButton("Guardar") { _, _ ->
            val title = inputTitle.text.toString()
            val description = inputDescription.text.toString()

            // Verifica que el título no esté vacío antes de guardar
            if (title.isNotEmpty()) {
                val newTask = Tareas(0, title, description)
                saveTask(newTask)
                Toast.makeText(requireContext(), "Tarea guardada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun saveTask(newTask: Tareas) {
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = (requireActivity().application as MyAplication).room.tareaDao()
            dao.saveTask(newTask)

            // Obtén los datos actualizados en el hilo de fondo
            val updatedData = dao.getAllTask()

            // Cambiar al hilo principal para actualizar la interfaz de usuario
            lifecycleScope.launch(Dispatchers.Main) {
                // Ahora llama a updateData() en el hilo principal
                tareasAdapter.updateData(updatedData)
            }
        }
    }




    private fun gotoRegister() {
        logged = false;
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    private fun deleteUser(user: String?) {
        user?.let { username ->
            lifecycleScope.launch(Dispatchers.IO) {
                // Acceder al DataStore y eliminar el usuario
                DataStoreManager.deleteUser(requireContext(), username)
            }
        }
    }

    private fun ConfirmationDialog(message: String, actionOnConfirmation: () -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage(message)
        builder.setPositiveButton("Sí") { dialog, which ->
            // Acciones a realizar si el usuario confirma
            actionOnConfirmation.invoke()
        }
        builder.setNegativeButton("No") { dialog, which ->
            // Acciones a realizar si el usuario cancela
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onTareaClicked(tarea: Tareas) {
        tareasAdapter.listener.mostrarOpciones(tarea)
    }
    override fun onEliminarTarea(tarea: Tareas) {
        // Lógica para eliminar la tarea de la base de datos Room
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = (requireActivity().application as MyAplication).room.tareaDao()
            dao.deleteTask(tarea)

            // Obtén los datos actualizados en el hilo de fondo
            val updatedData = dao.getAllTask()

            // Cambiar al hilo principal para actualizar la interfaz de usuario
            lifecycleScope.launch(Dispatchers.Main) {
                // Ahora llama a updateData() en el hilo principal
                tareasAdapter.updateData(updatedData)
            }
        }
    }
    override fun onModificarTarea(tarea: Tareas) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Modificar Tarea")

        val inputLayout = LinearLayout(requireContext())
        inputLayout.orientation = LinearLayout.VERTICAL

        val inputTitle = EditText(requireContext())
        inputTitle.hint = "Nuevo Título"
        inputTitle.setText(tarea.tittle)  // Establecer el título actual como texto predeterminado
        inputLayout.addView(inputTitle)

        val inputDescription = EditText(requireContext())
        inputDescription.hint = "Nueva Descripción"
        inputDescription.setText(tarea.description)  // Establecer la descripción actual como texto predeterminado
        inputLayout.addView(inputDescription)

        builder.setView(inputLayout)

        builder.setPositiveButton("Guardar") { _, _ ->
            val newTitle = inputTitle.text.toString()
            val newDescription = inputDescription.text.toString()

            // Actualizar la tarea si el nuevo título no está vacío
            if (newTitle.isNotEmpty()) {
                val updatedTask = tarea.copy(tittle = newTitle, description = newDescription)
                updateTask(updatedTask)
                Toast.makeText(requireContext(), "Tarea modificada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "El nuevo título no puede estar vacío",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateTask(updatedTask: Tareas) {
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = (requireActivity().application as MyAplication).room.tareaDao()
            dao.updateTask(updatedTask)

            // Obtén los datos actualizados en el hilo de fondo
            val updatedData = dao.getAllTask()

            // Cambiar al hilo principal para actualizar la interfaz de usuario
            lifecycleScope.launch(Dispatchers.Main) {
                // Ahora llama a updateData() en el hilo principal
                tareasAdapter.updateData(updatedData)
            }
        }
    }

    override fun mostrarOpciones(tarea: Tareas) {
        val opciones = arrayOf("Eliminar", "Modificar")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Opciones de tarea")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> onEliminarTarea(tarea) // Llamar a la interfaz para eliminar
                    1 -> onModificarTarea(tarea) // Llamar a la interfaz para modificar
                }
            }

        builder.create().show()
    }

}


