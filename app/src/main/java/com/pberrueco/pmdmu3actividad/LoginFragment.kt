package com.pberrueco.pmdmu3actividad

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pberrueco.pmdmu3actividad.databinding.FragmentLogin2Binding
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLogin2Binding
    private val binding: FragmentLogin2Binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.btnAccess.setOnClickListener {
            var userEt = binding.etUsername.text.toString()
            Log.d("EncuentraU", "$userEt")
            var passEt = binding.etPassword.text.toString()
            Log.d("EncuentraP", "$passEt")
            var user: String = obtainUser()
            var pass: String = obtainPass()

            var valida = validador(userEt, passEt, user, pass)

            if (valida){
                Toast.makeText(context,"LOGIN OK", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"FALLO LOGIN", Toast.LENGTH_LONG).show()
            }
        }
        */
        binding.btnAccess.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val userEt = binding.etUsername.text.toString()
                Log.d("EncuentraU", "$userEt")
                val passEt = binding.etPassword.text.toString()
                Log.d("EncuentraP", "$passEt")

                val obtainedUser = obtainUser()
                val obtainedPass = obtainPass()

                val valida = validador(userEt, passEt, obtainedUser, obtainedPass)

                if (valida) {
                    Toast.makeText(context, "LOGIN OK", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "FALLO LOGIN", Toast.LENGTH_LONG).show()
                }
            }
        }


        binding.btnRegister.setOnClickListener {
            Log.d("LoginFrag", "Button clicked")
            toRegister()
        }
    }

    private fun validador(userEt: String, passEt: String, user: String, pass: String): Boolean {
        var valida = false
        if (user.isNotEmpty()  && (userEt.equals(user) && passEt.equals(pass))){
            valida = true
        }
        return valida
    }

    private fun toRegister() {
        Log.d("LoginFrag", "Navigating to RegisterFrag")
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }


    private suspend fun obtainUser(): String {
        val userDeferred = CompletableDeferred<String>()

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.getUser(requireContext()).collect { user ->
                userDeferred.complete(user)
            }
        }

        val userObtained = userDeferred.await()
        Log.d("EncuentraUU", "userObtained es $userObtained")
        return userObtained
    }

    private suspend fun obtainPass(): String {
        val passDeferred = CompletableDeferred<String>()

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.getPassword(requireContext()).collect { pass ->
                passDeferred.complete(pass)
            }
        }

        val passObtained = passDeferred.await()
        Log.d("EncuentraPP", "passObtained es $passObtained")
        return passObtained
    }


}