package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectoferreteria.dataBase.AppDataBaseProduct
import com.example.proyectoferreteria.databinding.ActivityDetalleProductoBinding
import com.example.proyectoferreteria.utils.Constants
import java.util.concurrent.Executors

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
        eventos()
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

    fun eventos(){
        binding.fblAnadirProducto.setOnClickListener {
            startActivity(Intent(this, registroProduct::class.java))
        }

        adapter.setOnClickListenerProductEdit={
            val bundle=Bundle().apply {
                putSerializable(Constants.KEY_PRODUCT,it)
            }
            startActivity(Intent(this, registroProduct::class.java).apply {
                putExtras(bundle)
            })
        }

        adapter.setOnClickListenerProductDelete={
            Executors.newSingleThreadExecutor().execute(){
                appDataBaseProduct.productDao().delete(it)
                runOnUiThread {
                    Toast.makeText(this, "Producto Eliminado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}