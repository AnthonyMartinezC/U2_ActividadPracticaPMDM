package com.pmdm.u2_practicavistasavanzadas.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmdm.u2_practicavistasavanzadas.R
import com.pmdm.u2_practicavistasavanzadas.models.Autonomia

class AutonomiaAdapter(
    private val autonomias: List<Autonomia>,
    private val onClick: (Autonomia) -> Unit,
    private val onEdit: (Autonomia) -> Unit,
    private val onDelete: (Autonomia) -> Unit
) : RecyclerView.Adapter<AutonomiaAdapter.AutonomiaViewHolder>() {

    class AutonomiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAutonomia: TextView = itemView.findViewById(R.id.tvAutonomia)
        val ivAutonomia: ImageView = itemView.findViewById(R.id.ivAutonomia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutonomiaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_autonomia, parent, false)
        return AutonomiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutonomiaViewHolder, position: Int) {
        val autonomia = autonomias[position]
        holder.tvAutonomia.text = autonomia.nombre

        // Cargar la imagen de la bandera usando Glide
        Glide.with(holder.itemView.context)
            .load(autonomia.bandera)
            .into(holder.ivAutonomia)



        // Configurar clic simple
        holder.itemView.setOnClickListener {
            onClick(autonomia)
        }

        // Configurar clic largo para mostrar menú contextual
        holder.itemView.setOnLongClickListener {
            val popup = PopupMenu(holder.itemView.context, holder.itemView)
            popup.inflate(R.menu.context_menu)

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        onEdit(autonomia) // Llama a la función de edición
                        true
                    }
                    R.id.action_delete -> {
                        onDelete(autonomia) // Llama a la función de eliminación
                        true
                    }
                    else -> false
                }
            }
            popup.show()
            true
        }
    }

    override fun getItemCount(): Int = autonomias.size
}
