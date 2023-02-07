package com.example.proyectoferreteria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoferreteria.databinding.ItemEmpleadosBinding
import com.example.proyectoferreteria.model.User

class EmpleadoAdapter (var empleados:List<User> = emptyList()): RecyclerView.Adapter<EmpleadoAdapter.EmpleadoAdapterViewHolder>() {

    //definir las funciones para manipular los registro
    //Crear el ViewHolder
    inner class EmpleadoAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private var binding : ItemEmpleadosBinding=ItemEmpleadosBinding.bind(itemView)
        fun bind(usuario: User){
            binding.txtNombre.text =usuario.nombre+" "+usuario.apellido
            binding.txtEmail.text = usuario.email
            binding.txtItemUsuario.text = usuario.usuario
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpleadoAdapterViewHolder {
        //Para implemetar una vista
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_empleados,parent,false)
        return EmpleadoAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmpleadoAdapterViewHolder, position: Int) {
        //posicion de cada elemenot en la vista
        val usuario = empleados[position]
        holder.bind(usuario)
    }

    override fun getItemCount(): Int {
        return empleados.size
    }

    //Editar

    fun updateListUsers(empleados:List<User>){
        this.empleados=empleados
        notifyDataSetChanged()
    }
}