package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import com.example.proyectoferreteria.dataBase.AppDataBaseProduct
import com.example.proyectoferreteria.databinding.ActivityDetalleProductoBinding
import com.example.proyectoferreteria.model.Product
import com.example.proyectoferreteria.model.User
import com.example.proyectoferreteria.utils.Constants
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class detalleProducto : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleProductoBinding
    private lateinit var search: SearchView

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
        search = binding.searchView
        cargarAdaptador()
        cargarDatos()
        regresar()
        eventos()

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
        binding.rvListaProductos.adapter = adapter
    }

    fun cargarDatos() {
        appDataBaseProduct.productDao().getProduct().observe(this, { productos ->
            adapter.updateListProducts(productos)
        })
    }

    fun regresar() {
        binding.fblRegresarUser.setOnClickListener {
            val bundleInicio = Bundle().apply {
                putSerializable(Constants.KEY_USER, usuarioIngreso())
            }
            startActivity(Intent(this, activity_inicio::class.java).apply {
                putExtras(bundleInicio)
            })
        }
    }

    fun eventos() {
        binding.fblAnadirProducto.setOnClickListener {
            startActivity(Intent(this, registroProduct::class.java))
        }

        adapter.setOnClickListenerProductEdit = {
            val bundle = Bundle().apply {
                putSerializable(Constants.KEY_PRODUCT, it)
            }
            startActivity(Intent(this, registroProduct::class.java).apply {
                putExtras(bundle)
            })
        }

        adapter.setOnClickListenerProductDelete = {
            Executors.newSingleThreadExecutor().execute() {
                appDataBaseProduct.productDao().delete(it)
                runOnUiThread {
                    Toast.makeText(this, "Producto Eliminado", Toast.LENGTH_LONG).show()
                }
            }
        }

        adapter.setOnClickDetalle = {
            val bundle = Bundle().apply {
                putSerializable(Constants.KEY_PRODUCT, it)
                putSerializable(Constants.KEY_USER, usuarioIngreso())
            }
            startActivity(Intent(this, detalles::class.java).apply {
                putExtras(bundle)
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
            val filteredList = ArrayList<Product>()
            for (i in adapter.productos) {
                if (i.nombre.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.updateListProducts(filteredList)
            }
        }
    }
}