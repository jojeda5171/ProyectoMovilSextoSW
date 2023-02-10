package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectoferreteria.dataBase.AppDataBaseProduct
import com.example.proyectoferreteria.databinding.ActivityDetallesBinding
import com.example.proyectoferreteria.model.Product
import com.example.proyectoferreteria.model.User
import com.example.proyectoferreteria.utils.Constants
import com.squareup.picasso.Picasso

class detalles : AppCompatActivity() {
    private lateinit var binding: ActivityDetallesBinding
    private var id = 0
    private val appDataBaseProduct: AppDataBaseProduct by lazy {
        AppDataBaseProduct.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetallesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicizalizar()
        evento()
    }

    private fun inicizalizar() {
        val bundle = intent.extras
        bundle?.let {
            val product = bundle.getSerializable(Constants.KEY_PRODUCT) as Product
            binding.txtDetalleNombre.setText(product.nombre.toUpperCase())
            binding.txtDetalleTipo.setText(product.tipo)
            binding.txtDetalleCantidad.setText(product.cantidad+" unidades")
            binding.txtDetalleCosto.setText(product.costo+"$")
            binding.txtDetalleDecripcion.setText(product.descripcion)
            Picasso.get().load(product.foto).error(R.drawable.ic_launcher_background)
                .into(binding.imageView2)
        } ?: kotlin.run {

        }

    }

    private fun evento() {
        binding.floatingActionButton.setOnClickListener {
            val bundleInicio = Bundle().apply {
                putSerializable(Constants.KEY_USER, usuarioIngreso())
            }
            startActivity(Intent(this, detalleProducto::class.java).apply {
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
