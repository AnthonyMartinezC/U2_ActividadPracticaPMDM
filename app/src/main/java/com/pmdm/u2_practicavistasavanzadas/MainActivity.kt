package com.pmdm.u2_practicavistasavanzadas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmdm.u2_practicavistasavanzadas.adapters.AutonomiaAdapter
import com.pmdm.u2_practicavistasavanzadas.models.Autonomia

class MainActivity : AppCompatActivity() {

    // Lista mutable para poder modificarla
    private val autonomias = mutableListOf(
        Autonomia(1, "Andalucía", R.drawable.andalucia),
        Autonomia(2, "Cataluña", R.drawable.catalunia),
        Autonomia(3, "Madrid", R.drawable.comunidaddemadrid),
        Autonomia(4, "Galicia", R.drawable.galicia),
        Autonomia(5, "Valencia", R.drawable.valencia),
        Autonomia(6, "Castilla y León", R.drawable.castillayleon),
        Autonomia(7, "Castilla-La Mancha", R.drawable.castillalamancha),
        Autonomia(8, "País Vasco", R.drawable.paisvasco),
        Autonomia(9, "Extremadura", R.drawable.extremadura),
        Autonomia(10, "Aragón", R.drawable.aragon),
        Autonomia(11, "Cantabria", R.drawable.cantabria),
        Autonomia(12, "Murcia", R.drawable.murcia),
        Autonomia(13, "Navarra", R.drawable.navarra),
        Autonomia(14, "La Rioja", R.drawable.larioja),
        Autonomia(15, "Islas Baleares", R.drawable.islabaleares),
        Autonomia(16, "Canarias", R.drawable.canarias),
        Autonomia(17, "Asturias", R.drawable.asturias)
    )

    // Copia de la lista original para recargar
    private val listaOriginal = autonomias.toList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AutonomiaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar la Toolbar personalizada como barra de acciones
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar el título de la Toolbar
        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        // Ajustar paddings según los insets del sistema (barra de estado y navegación)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar el adaptador con eventos de clic
        adapter = AutonomiaAdapter(
            autonomias = autonomias,
            onClick = { autonomia ->
                Toast.makeText(this, "Yo soy de ${autonomia.nombre}", Toast.LENGTH_SHORT).show()
            },
            onEdit = { autonomia ->
                editarAutonomia(autonomia) // Llama a la función de edición
            },
            onDelete = { autonomia ->
                eliminarAutonomia(autonomia) // Llama a la función de eliminación
            }
        )
        recyclerView.adapter = adapter

        recyclerView.adapter = adapter
    }

    // Crear el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Configurar acciones de los botones del menú: limpiar y recargar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_recargar -> {
                Toast.makeText(this, "Recargando lista...", Toast.LENGTH_SHORT).show()
                recargarLista()
                true
            }

            R.id.menu_limpiar -> {
                Toast.makeText(this, "Limpiando lista...", Toast.LENGTH_SHORT).show()
                limpiarLista()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Función para limpiar la lista de autonomías
    @SuppressLint("NotifyDataSetChanged")
    private fun limpiarLista() {
        autonomias.clear() // Vaciar la lista
        adapter.notifyDataSetChanged() // Notificar al adaptador que la lista cambió
    }

    // Función para recargar la lista de autonomías
    @SuppressLint("NotifyDataSetChanged")
    private fun recargarLista() {
        autonomias.clear() // Limpiar la lista actual
        autonomias.addAll(listaOriginal) // Restaurar la lista original
        adapter.notifyDataSetChanged() // Notificar al adaptador que la lista cambió
    }


    private fun editarAutonomia(autonomia: Autonomia) {
        val input = EditText(this)
        input.setText(autonomia.nombre)

        AlertDialog.Builder(this)
            .setTitle("Editar Autonomía")
            .setView(input)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevoNombre = input.text.toString()
                val index = autonomias.indexOf(autonomia)
                if (index != -1) {
                    autonomias[index] = autonomia.copy(nombre = nuevoNombre)
                    adapter.notifyItemChanged(index)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarAutonomia(autonomia: Autonomia) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Autonomía")
            .setMessage("¿Estás seguro de que deseas eliminar ${autonomia.nombre}?")
            .setPositiveButton("Sí") { _, _ ->
                val index = autonomias.indexOf(autonomia)
                if (index != -1) {
                    autonomias.removeAt(index)
                    adapter.notifyItemRemoved(index)
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

}
