package com.pberrueco.pmdmu3actividad.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pberrueco.pmdmu3actividad.data.datastore.DataStoreManager
import com.pberrueco.pmdmu3actividad.databinding.FragmentRegister2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {
    private lateinit var _binding: FragmentRegister2Binding
    private val binding: FragmentRegister2Binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegister2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister2.setOnClickListener{
            createUser()
        }


    }

    private fun createUser() {
        val userName = binding.etUsername2.text.toString()
        val password = binding.etPassword2.text.toString()
        val password2 = binding.etPassword3.text.toString()
        if(!userName.isNullOrEmpty() && !password.isNullOrEmpty() && !password2.isNullOrEmpty()){
            if(password == password2) {
                //Lanzamos la corrutina
                lifecycleScope.launch (Dispatchers.IO){
                    DataStoreManager.saveUser(requireContext(), userName, password)
                }
                Toast.makeText(context,"Usuario creado", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            } else{
                Toast.makeText(context,"La contraseña debe coincidir", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context,"Rellene todos los campos", Toast.LENGTH_SHORT).show()
            }
    }


}

