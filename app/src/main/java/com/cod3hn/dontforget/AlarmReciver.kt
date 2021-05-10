package com.cod3hn.dontforget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.cod3hn.dontforget.`class`.Notifications
import com.cod3hn.dontforget.`class`.notificacion
import com.cod3hn.dontforget.`class`.saveAlarm
import java.util.*

class AlarmReciver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent!!.action.equals("com.cod3hn.dontforget")){
            var b=intent.extras
            Toast.makeText(context,b?.getString("message"),Toast.LENGTH_LONG).show()
            val notifyMe=Notifications()
            notifyMe.Notify(context!!, b!!.getString("message")!!,10)
        }
        else if(intent!!.action.equals("android.intent.action.BOOT_COMPLETED")){

            val saveData= saveAlarm(context!!)
            saveData.setAlarm()
        }
    }




}