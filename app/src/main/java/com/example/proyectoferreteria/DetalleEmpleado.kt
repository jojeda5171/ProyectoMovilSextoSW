package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectoferreteria.dataBase.AppDataBase
import com.example.proyectoferreteria.databinding.ActivityDetalleEmpleadoBinding
import com.example.proyectoferreteria.model.User
import com.example.proyectoferreteria.utils.Constants

class DetalleEmpleado : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleEmpleadoBinding

    //Acceder al adaptador
    //Lazy ayuda que solo se inicialize cuando sea necesario
    private val adapter: EmpleadoAdapter by lazy {
        EmpleadoAdapter()
    }

    //Acceder a la instancia de la base de datos

    private val appDataBase: AppDataBase by lazy {
        AppDataBase.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleEmpleadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cargarAdaptador()
        cargarDatos()
        regresar()
    }

    fun cargarAdaptador() {
        binding.rvListaEmpleados.adapter = adapter
    }

    fun cargarDatos() {
        appDataBase.userDao().getUsers().observe(this, { usuarios ->
            adapter.updateListUsers(usuarios)
        })
    }

    fun regresar(){
        binding.fblRegresar.setOnClickListener {
            val bundleInicio = Bundle().apply {
                putSerializable(Constants.KEY_USER, usuarioIngreso())
            }
            startActivity(Intent(this, activity_inicio::class.java).apply {
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