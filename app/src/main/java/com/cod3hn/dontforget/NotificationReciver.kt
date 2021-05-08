package com.cod3hn.dontforget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cod3hn.dontforget.`class`.PreUtil
import com.cod3hn.dontforget.`class`.appConst
import com.cod3hn.dontforget.`class`.notificacion

class NotificationReciver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            appConst.ACTION_STOP->{
                dashBoard.removeAlarm(context)
                PreUtil.setTimerState(dashBoard.TimerState.Stopped, context)
                notificacion.hideTimerNotification(context)
            }
            appConst.ACTION_PAUSE->{
                var secondsRemaining = PreUtil.getSecondsRemaining(context)
                val alarmSetTime = PreUtil.getAlarmTime(context)
                val nowSeconds = dashBoard.nowSeconds

                secondsRemaining-= nowSeconds- alarmSetTime

                PreUtil.setSecondsRemaining(secondsRemaining, context)
                dashBoard.removeAlarm(context)
                PreUtil.setTimerState(dashBoard.TimerState.Paused, context)
                notificacion.showTimerPaused(context)
            }
            appConst.ACTION_RESUME ->{
                val secondsRemaining = PreUtil.getSecondsRemaining(context)
                val wakeUpTime = dashBoard.setAlarm(context, dashBoard.nowSeconds, secondsRemaining)
                PreUtil.setTimerState(dashBoard.TimerState.Running, context)
                notificacion.showTimerRunning(context, wakeUpTime)
            }
            appConst.ACTION_START->{
                val minutesRemaining = PreUtil.getTimerLengtj(context)
                val secondsRemaining = minutesRemaining*60L
                val wakeUpTime = dashBoard.setAlarm(context, dashBoard.nowSeconds, secondsRemaining)
                PreUtil.setTimerState(dashBoard.TimerState.Running, context)
                PreUtil.setSecondsRemaining(secondsRemaining, context)
                notificacion.showTimerRunning(context, wakeUpTime)

            }
        }
    }
}