package com.cod3hn.dontforget.`class`

import android.content.Context
import android.content.Intent
import com.cod3hn.dontforget.dashBoard
import com.cod3hn.dontforget.db.DbHelper

class functions {

    fun finalizar(context:Context){
        var _ID = tiempo(context).ObtenerID()
        DbHelper(context).finished(_ID)

        val intent = Intent(context, dashBoard::class.java)

    }

}