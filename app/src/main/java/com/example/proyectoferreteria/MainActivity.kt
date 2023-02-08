package com.example.proyectoferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectoferreteria.dataBase.AppDataBase
import com.example.proyectoferreteria.databinding.ActivityMainBinding
import com.example.proyectoferreteria.utils.Constants
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var codigo = ""
    //var binding: ActivityMainBinding

    private val appDataBase: AppDataBase by lazy {
        AppDataBase.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.openRegistrarUser()
        this.login()
    }

    fun openRegistrarUser() {
        binding.btnRegistrar.setOnClickListener {
            //this.finish()
            startActivity(Intent(this, activity_registroUser::class.java))
        }
    }

    fun login() {
        binding.btnAcceder.setOnClickListener {

            if (binding.edtUsuario.text.toString().equals("") || binding.edtPass.text.toString()
                    .equals("")
            ) {
                runOnUiThread {
                    Toast.makeText(this, "Ingrese sus credenciales", Toast.LENGTH_LONG).show()
                    binding.edtUsuario.requestFocus()
                }
            } else {
                Executors.newSingleThreadExecutor().execute() {
                    val usuario = appDataBase.userDao().getUserByUserAndPass(
                        binding.edtUsuario.text.toString(),
                        binding.edtPass.text.toString()
                    )
                    if (usuario == null) {
                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "Ingrese sus credenciales correctamente",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.edtUsuario.requestFocus()
                        }
                    } else {
                        this.codigo = usuario.usuario
                        runOnUiThread {
                            val bundleMain=Bundle().apply {
                                putSerializable(Constants.KEY_USER, usuario)
                            }
                            startActivity(Intent(this, activity_inicio()::class.java).apply {
                                putExtras(bundleMain)
                            })
                            Toast.makeText(
                                this,
                                "Bienvenido: " + usuario.usuario,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

    }
}