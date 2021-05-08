package com.cod3hn.dontforget

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cod3hn.dontforget.db.DbHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //variable de animacion
        val animacion = AnimationUtils.loadAnimation(this , R.anim.toup_down)
        //obtener los datos
        val img = findViewById<ImageView>(R.id.imgLogo)

        img.animation = animacion

        //base de Datos

        //tiempo de duracion
       Handler().postDelayed({
           val newScreen = Intent(this, dashBoard::class.java)
           startActivity(newScreen)
           this.finish()
       }, 4000)

    }


}