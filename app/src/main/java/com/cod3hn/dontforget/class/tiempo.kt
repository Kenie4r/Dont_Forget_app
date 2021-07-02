package com.cod3hn.dontforget.`class`

import android.content.Context

class tiempo( context: Context) {
    val SHARED_NAME = "Mydtb"
    val SHARED_MINUTES = "tiempo"
    val SHARED_ID = "ID"
    val storage = context.getSharedPreferences(SHARED_NAME, 0)


    fun saveMinutos(minutos:Int){
        storage.edit().putInt(SHARED_MINUTES, minutos).apply()
    }

    fun getTiempo():Int{
        var minutos = storage.getInt(SHARED_MINUTES, 0)

        return minutos
    }

    fun saveID(_ID:Int){
        storage.edit().putInt(SHARED_ID, _ID).apply()
    }
    fun ObtenerID():Int{
        var _ID = storage.getInt(SHARED_ID, 0)

        return _ID
    }

}