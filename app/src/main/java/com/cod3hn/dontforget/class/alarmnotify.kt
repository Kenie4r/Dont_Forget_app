package com.cod3hn.dontforget.`class`

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.cod3hn.dontforget.R
import com.cod3hn.dontforget.dashBoard
class  Notifications(){

    val NOTIFIYTAG="new request"
    fun Notify(context: Context,message:String,number:Int){
        val intent=Intent(context,dashBoard::class.java)

        val builder= NotificationCompat.Builder(context)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("¡No lo olvides!")
            .setContentText(message)
            .setNumber(number)
            .setSmallIcon(R.mipmap.clocks)
            .setContentIntent(PendingIntent.getActivity(context
                ,0,intent,PendingIntent.FLAG_UPDATE_CURRENT))
            .setAutoCancel(true)

        val nm=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFIYTAG, 123574, builder.build())
        }else{
            nm.notify(NOTIFIYTAG.hashCode(), builder.build())
        }

    }

}