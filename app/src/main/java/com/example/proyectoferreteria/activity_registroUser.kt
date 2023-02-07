package com.example.proyectoferreteria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectoferreteria.dataBase.AppDataBase
import com.example.proyectoferreteria.databinding.ActivityRegistroUserBinding
import com.example.proyectoferreteria.model.User
import com.example.proyectoferreteria.utils.Constants
import java.util.concurrent.Executors

class activity_registroUser : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroUserBinding
    private var id = 0
    private val appDataBase: AppDataBase by lazy {
        AppDataBase.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistroUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicizalizar()
        evento()
    }

    private fun inicizalizar() {
        val bundle = intent.extras
        bundle?.let {
            val user = bundle.getSerializable(Constants.KEY_USER) as User
            binding.btnRegAddUser.text = "Actualizar"
            id = user.id
            binding.edtRegNombre.setText(user.nombre)
            binding.edtRegApellido.setText(user.apellido)
            binding.edtRegEmail.setText(user.email)
            binding.edtRegDireccion.setText(user.direccion)
            binding.edtRegUser.setText(user.usuario)
            binding.edtRegPass.setText(user.pass)
        } ?: kotlin.run {
            binding.btnRegAddUser.text = "Registar"
            binding.edtRegNombre.setText("")
            binding.edtRegApellido.setText("")
            binding.edtRegEmail.setText("")
            binding.edtRegDireccion.setText("")
            binding.edtRegUser.setText("")
            binding.edtRegPass.setText("")
        }
        binding.edtRegNombre.requestFocus()
    }

    private fun evento() {
        binding.btnRegAddUser.setOnClickListener {
            val nombre = binding.edtRegNombre.text.toString()
            val apellido = binding.edtRegApellido.text.toString()
            val email = binding.edtRegEmail.text.toString()
            val direccion = binding.edtRegDireccion.text.toString()
            val user = binding.edtRegUser.text.toString()
            val pass = binding.edtRegPass.text.toString()
            if (id == 0) {
                agregar(User(0, nombre, apellido, email, direccion, user, pass))
            } else {
                //editar(Pet(id, nombre, raza, preferencias))
            }
        }
    }

    fun agregar(user: User) {
        Executors.newSingleThreadExecutor().execute() {
            appDataBase.userDao().insert(user)
            runOnUiThread {
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        }
    }
}