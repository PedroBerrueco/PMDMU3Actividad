package com.pberrueco.pmdmu3actividad.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pberrueco.pmdmu3actividad.data.datastore.DataStoreManager
import com.pberrueco.pmdmu3actividad.databinding.FragmentLogin2Binding
import com.pberrueco.pmdmu3actividad.ui.task.TaskActivity
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

        binding.btnAccess.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val userEt = binding.etUsername.text.toString()
                val passEt = binding.etPassword.text.toString()
                val obtainedUser = obtainUser()
                val obtainedPass = obtainPass()

                val valida = validador(userEt, passEt, obtainedUser, obtainedPass)

                if (valida) {
                    totask(userEt)
                } else {
                    Toast.makeText(context, "FALLO LOGIN", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            toRegister()
        }
    }

    private fun totask(userEt: String) {
        val logged = true;
        val intent = Intent(requireContext(), TaskActivity::class.java)
        intent.putExtra("user", userEt)
        intent.putExtra("logged", logged)

        startActivity(intent)
    }

    private fun validador(userEt: String, passEt: String, user: String, pass: String): Boolean {
        var valida = false
        if (user.isNotEmpty()  && (userEt.equals(user) && passEt.equals(pass))){
            valida = true
        }
        return valida
    }

    private fun toRegister() {
        val action =
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            //com.pberrueco.pmdmu3actividad.LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
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
        return passObtained
    }


}