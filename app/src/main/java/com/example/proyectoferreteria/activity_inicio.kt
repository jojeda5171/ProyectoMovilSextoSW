package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectoferreteria.dataBase.AppDataBase
import com.example.proyectoferreteria.databinding.ActivityInicioBinding
import com.example.proyectoferreteria.model.User
import com.example.proyectoferreteria.utils.Constants

class activity_inicio : AppCompatActivity() {

    lateinit var binding: ActivityInicioBinding

    private val appDataBase: AppDataBase by lazy {
        AppDataBase.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuarioIngreso()
        eventos()
    }

    fun eventos() {
        binding.btnEmpleados.setOnClickListener {
            startActivity(Intent(this, DetalleEmpleado::class.java))
        }

        binding.btnProductos.setOnClickListener {
            startActivity(Intent(this, detalleProducto::class.java))
        }

        binding.btnPerfil.setOnClickListener {
            val bundleInicio = Bundle().apply {
                putSerializable(Constants.KEY_USER, usuarioIngreso())
            }
            startActivity(Intent(this, activity_registroUser()::class.java).apply {
                putExtras(bundleInicio)
            })
        }
    }

    fun usuarioIngreso(): User? {
        val bundle = intent.extras
        bundle?.let {
            val empleado = bundle.getSerializable(Constants.KEY_USER) as User
            return empleado
        }
        return null
    }
}