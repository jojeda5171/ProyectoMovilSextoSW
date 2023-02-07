package com.example.proyectoferreteria.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectoferreteria.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDataBaseProduct : RoomDatabase(){
    //Definir el DAO a utilizar
    abstract fun productDao():ProductDAO

    //Definir la instancia de la base de datos
    companion object {
        var instacia: AppDataBaseProduct? = null

        //manejar la instancia
        fun getInstance(context: Context): AppDataBaseProduct {
            if (instacia == null) {
                instacia = Room.databaseBuilder(
                    context,
                    AppDataBaseProduct::class.java,
                    "bdProducts"
                ).build()
            }
            return instacia as AppDataBaseProduct
        }
    }
}