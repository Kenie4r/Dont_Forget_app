package com.cod3hn.dontforget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.transaction
import com.cod3hn.dontforget.fragments.actual_task
import com.cod3hn.dontforget.fragments.new_task

class dashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        //abrir fragment

        val tareasActual = actual_task()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_view, tareasActual)
            addToBackStack(null)
            commit()
        }

    }

    fun crearTask( view: View){
        val pantalla = new_task()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_view, pantalla)
            addToBackStack(null)
            commit()
        }
    }
    fun abrirDashboard(view: View){
        val pantalla = actual_task()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fr_view, pantalla)
            addToBackStack(null)
            commit()
        }
    }


}