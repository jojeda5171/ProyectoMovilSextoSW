package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import com.example.proyectoferreteria.dataBase.AppDataBase
import com.example.proyectoferreteria.databinding.ActivityDetalleEmpleadoBinding
import com.example.proyectoferreteria.model.Product
import com.example.proyectoferreteria.model.User
import com.example.proyectoferreteria.utils.Constants
import java.util.*
import kotlin.collections.ArrayList

class DetalleEmpleado : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleEmpleadoBinding
    private lateinit var search: SearchView

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
        search = binding.searchView2
        cargarAdaptador()
        cargarDatos()
        regresar()

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
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

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<User>()
            for (i in adapter.empleados) {
                if (i.nombre.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No se encontro información", Toast.LENGTH_SHORT).show()
            } else {
                adapter.updateListUsers(filteredList)
            }
        }
    }
}