package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectoferreteria.dataBase.AppDataBaseProduct
import com.example.proyectoferreteria.databinding.ActivityDetalleProductoBinding

class detalleProducto : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleProductoBinding

    //Acceder al adaptador
    //Lazy ayuda que solo se inicialize cuando sea necesario
    private val adapter: ProductoAdapter by lazy {
        ProductoAdapter()
    }

    //Acceder a la instancia de la base de datos

    private val appDataBaseProduct: AppDataBaseProduct by lazy {
        AppDataBaseProduct.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cargarAdaptador()
        cargarDatos()
        regresar()
        registrar()
    }

    fun cargarAdaptador() {
        binding.rvListaProductos.adapter = adapter
    }

    fun cargarDatos() {
        appDataBaseProduct.productDao().getProduct().observe(this, { productos ->
            adapter.updateListProducts(productos)
        })
    }

    fun regresar(){
        binding.fblRegresarUser.setOnClickListener {
            startActivity(Intent(this, activity_inicio::class.java))
        }
    }

    fun registrar(){
        binding.fblAnadirProducto.setOnClickListener {
            startActivity(Intent(this, registroProduct::class.java))
        }
    }
}