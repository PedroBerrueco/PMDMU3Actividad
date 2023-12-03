package com.pberrueco.pmdmu3actividad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pberrueco.pmdmu3actividad.databinding.ActivityMainBinding
import com.pberrueco.pmdmu3actividad.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityTaskBinding
    private val binding: ActivityTaskBinding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra("user")
        val logged = intent.getBooleanExtra("logged", false)

        val taskFragment = TaskFragment().apply {
            arguments = Bundle().apply {
                putString("user", user)
                putBoolean("logged", logged)
            }
        }

        // Agregar el fragmento al contenedor
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .commit()
    }
}