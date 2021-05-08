package com.cod3hn.dontforget.`class`

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.cod3hn.dontforget.NotificationReciver
import com.cod3hn.dontforget.R
import com.cod3hn.dontforget.dashBoard
import java.text.SimpleDateFormat
import java.util.*


class notificacion{
    companion object {
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "Timer App Timer"
        private const val TIMER_ID = 0

        fun showTimerExpired(context: Context){
            val startIntent = Intent(context, NotificationReciver::class.java)
            startIntent.action = appConst.ACTION_START
            val startPendingIntent = PendingIntent.getBroadcast(context,
                0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("¡Tiempo acabado!")
                .setContentText("Ya es hora de descanzar")
                .setContentIntent(getPendingIntentWithStack(context, dashBoard::class.java))
                .addAction(R.drawable.ic_baseline_play_arrow_24, "Start", startPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun showTimerRunning(context: Context, wakeUpTime: Long){
            val stopIntent = Intent(context, NotificationReciver::class.java)
            stopIntent.action = appConst.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(context,
                0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val pauseIntent = Intent(context, NotificationReciver::class.java)
            pauseIntent.action = appConst.ACTION_PAUSE
            val pausePendingIntent = PendingIntent.getBroadcast(context,
                0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val df = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("¡Ya es hora de trabajar!")
                .setContentText("El trabajo terminara a las: ${df.format(Date(wakeUpTime))}")
                .setContentIntent(getPendingIntentWithStack(context, dashBoard::class.java))
                .setOngoing(true)
                .addAction(R.drawable.ic_baseline_remove_circle_24, "Terminar", stopPendingIntent)
                .addAction(R.drawable.ic_baseline_pause_circle_filled_24, "Pausar", pausePendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun showTimerPaused(context: Context){
            val resumeIntent = Intent(context, NotificationReciver::class.java)
            resumeIntent.action = appConst.ACTION_RESUME
            val resumePendingIntent = PendingIntent.getBroadcast(context,
                0, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("¿Ya es hora del descanzo?")
                .setContentText("¿Podemos continuar?")
                .setContentIntent(getPendingIntentWithStack(context, dashBoard::class.java))
                .setOngoing(true)
                .addAction(R.drawable.ic_baseline_play_arrow_24, "Continuar", resumePendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun hideTimerNotification(context: Context){
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(TIMER_ID)
        }

        private fun getBasicNotificationBuilder(context: Context, channelId: String, playSound: Boolean)
                : NotificationCompat.Builder{
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.clocks)
                .setAutoCancel(true)
                .setDefaults(0)
            if (playSound) nBuilder.setSound(notificationSound)
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent{
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        @TargetApi(26)
        private fun NotificationManager.createNotificationChannel(channelID: String,
                                                                  channelName: String,
                                                                  playSound: Boolean){
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



     lateinit var  notificationManager : NotificationManager
    lateinit var channel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val CHANNEL_ID = "com.cod3hn.dontforget"
    private val DESCRIPTION = "Test notification"

    fun crearNotificacion(context: Context){
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //val intent = Intent(context, dashBoard()::class.java)
        //val intentoPendiente = PendingIntent( CHANNEL_ID , 0 , intent , 0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = NotificationChannel(CHANNEL_ID, DESCRIPTION, NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            channel.enableVibration(true)

            notificationManager.createNotificationChannel(channel)



            builder = Notification.Builder(context, CHANNEL_ID).apply {
                setContentTitle("¡No lo olvides!")
                setContentText("Una de tus tareas está apunto de comenzar")
                setSmallIcon(R.mipmap.clocks)
            }
        }else{
            builder = Notification.Builder(context).apply {
                setContentTitle("¡No lo olvides!")
                setContentText("Una de tus tareas está apunto de comenzar")
                setSmallIcon(R.mipmap.clocks)
            }
        }
        notificationManager.notify(1234, builder.build())
    }


}