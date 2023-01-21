package com.example.proyectoferreteria.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectoferreteria.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    //Definir el DAO a utilizar
    abstract fun userDao(): UserDAO

    //Definir la instancia de la base de datos
    companion object {
        var instacia: AppDataBase? = null

        //manejar la instancia
        fun getInstance(context: Context): AppDataBase {
            if (instacia == null) {
                instacia = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    "bdUsers"
                ).build()
            }
            return instacia as AppDataBase
        }
    }
}