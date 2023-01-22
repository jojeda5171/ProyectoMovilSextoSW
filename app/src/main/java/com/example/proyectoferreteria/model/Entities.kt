package com.example.proyectoferreteria.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tblUser")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "nombre")
    var nombre: String,
    @ColumnInfo(name = "apellido")
    var apellido: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "direccion")
    var direccion: String,
    @ColumnInfo(name = "usuario")
    var usuario: String,
    @ColumnInfo(name = "pass")
    var pass: String
) : Serializable