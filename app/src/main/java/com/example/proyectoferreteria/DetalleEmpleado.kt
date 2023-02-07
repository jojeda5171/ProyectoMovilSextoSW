package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectoferreteria.dataBase.AppDataBase
import com.example.proyectoferreteria.databinding.ActivityDetalleEmpleadoBinding

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
            startActivity(Intent(this, activity_inicio::class.java))
        }
    }
}