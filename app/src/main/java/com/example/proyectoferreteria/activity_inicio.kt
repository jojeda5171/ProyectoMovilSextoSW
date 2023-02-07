package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.proyectoferreteria.databinding.ActivityInicioBinding

class activity_inicio : AppCompatActivity() {
    lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEmpleados.setOnClickListener {
            startActivity(Intent(this, DetalleEmpleado::class.java))
        }

        binding.btnProductos.setOnClickListener {
            //this.remplazarFragmento(Fragmen_Bodega())
            startActivity(Intent(this, detalleProducto::class.java))
        }

        binding.btnPerfil.setOnClickListener {
            //this.remplazarFragmento(Fragmen_Bodega())
            startActivity(Intent(this, DetalleEmpleado::class.java))
        }
    }
}