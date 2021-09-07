package com.cod3hn.dontforget.`class`

import android.annotation.TargetApi
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
    val CHANNEL_ID = "alarm_notify"
    val CHANNEL_NAME = "notifications"
    val Noti__ID = 123574
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
        nm.createNotificationChannel(CHANNEL_ID,CHANNEL_NAME, true )
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
           nm.notify(Noti__ID    , builder.build())
        }else if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFIYTAG, 123574, builder.build())
        }else{
            nm.notify(NOTIFIYTAG.hashCode(), builder.build())
        }

    }
    @TargetApi(26)
    private fun NotificationManager.createNotificationChannel(channelID: String, channelName: String, playSound: Boolean){
        if (android.os.Build.VERSION.SDK_INT >=android.os.Build.VERSION_CODES.O){
            val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
            else NotificationManager.IMPORTANCE_LOW
            val nChannel = NotificationChannel(channelID, channelName, channelImportance)
            nChannel.enableLights(true)
            nChannel.lightColor = Color.BLUE
            this.createNotificationChannel(nChannel)
        }
    }

}