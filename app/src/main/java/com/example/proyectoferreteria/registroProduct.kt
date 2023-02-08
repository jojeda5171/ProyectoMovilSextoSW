package com.example.proyectoferreteria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectoferreteria.dataBase.AppDataBaseProduct
import com.example.proyectoferreteria.databinding.ActivityRegistroProductBinding
import com.example.proyectoferreteria.model.Product
import com.example.proyectoferreteria.utils.Constants
import java.util.concurrent.Executors

class registroProduct : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroProductBinding
    private var id = 0
    private val appDataBaseProduct: AppDataBaseProduct by lazy {
        AppDataBaseProduct.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistroProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicizalizar()
        evento()
    }

    private fun inicizalizar() {
        val bundle = intent.extras
        bundle?.let {
            val product = bundle.getSerializable(Constants.KEY_PRODUCT) as Product
            binding.btnRegistrarProducto.text = "Actualizar"
            id = product.id
            binding.edtNombreProducto.setText(product.nombre)
            binding.edtTipoProducto.setText(product.tipo)
            binding.edtCantidad.setText(product.cantidad)
            binding.edtCostoProducto.setText(product.costo)
            binding.edtDescripcionProducto.setText(product.descripcion)
            binding.edtUrlImg.setText(product.foto)
        } ?: kotlin.run {
            binding.btnRegistrarProducto.text = "Registar"
            binding.edtNombreProducto.setText("")
            binding.edtTipoProducto.setText("")
            binding.edtCantidad.setText("")
            binding.edtCostoProducto.setText("")
            binding.edtDescripcionProducto.setText("")
            binding.edtUrlImg.setText("")
        }
        binding.edtNombreProducto.requestFocus()
    }

    private fun evento() {
        binding.btnRegistrarProducto.setOnClickListener {
            val nombre = binding.edtNombreProducto.text.toString()
            val tipo = binding.edtTipoProducto.text.toString()
            val cantidad = binding.edtCantidad.text.toString()
            val costo = binding.edtCostoProducto.text.toString()
            val descripcion = binding.edtDescripcionProducto.text.toString()
            val foto = binding.edtUrlImg.text.toString()
            if (id == 0) {
                agregar(Product(0, nombre, tipo, costo, descripcion, cantidad, foto))
            } else {
                editar(Product(id, nombre, tipo, costo, descripcion, cantidad, foto))
            }
        }
    }

    fun agregar(product: Product) {
        Executors.newSingleThreadExecutor().execute() {
            appDataBaseProduct.productDao().insert(product)
            runOnUiThread {
                Toast.makeText(this, "Producto registrado correctamente", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        }
    }

    fun editar(product: Product) {
        Executors.newSingleThreadExecutor().execute() {
            appDataBaseProduct.productDao().update(product)
            runOnUiThread {
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        }
    }
}
