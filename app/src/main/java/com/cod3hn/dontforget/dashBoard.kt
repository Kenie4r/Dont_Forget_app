package com.cod3hn.dontforget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cod3hn.dontforget.fragments.actual_task
import com.cod3hn.dontforget.fragments.menu


class dashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        //abrir fragment

        val tareasActual = actual_task()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_view, tareasActual)
            commit()
        }

    }

    fun crearTask( view: View){
        var abrir = Intent( this, crearTareas::class.java)
        startActivity(abrir)

    }
    fun abrirDashboard(view: View){
        val pantalla = actual_task()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_view, pantalla)
            commit()
        }
    }
    fun abrirMenu(view: View){
        val pantalla = menu()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_view, pantalla)
            addToBackStack(null)
            commit()
        }
    }


}