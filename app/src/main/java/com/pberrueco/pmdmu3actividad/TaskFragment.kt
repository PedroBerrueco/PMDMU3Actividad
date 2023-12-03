package com.pberrueco.pmdmu3actividad

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pberrueco.pmdmu3actividad.databinding.FragmentTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskFragment : Fragment() {
    private lateinit var _binding: FragmentTaskBinding
    private val binding: FragmentTaskBinding get() = _binding

    var logged = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //logged = arguments?.getBoolean("logged") ?: false
        val user = arguments?.getString("user")
        binding.etTaskfrag.text = user

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



}