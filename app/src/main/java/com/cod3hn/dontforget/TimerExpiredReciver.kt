package com.cod3hn.dontforget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cod3hn.dontforget.`class`.PreUtil
import com.cod3hn.dontforget.`class`.notificacion

class TimerExpiredReciver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        notificacion.showTimerExpired(context)

        PreUtil.setTimerState(dashBoard.TimerState.Stopped, context)
        PreUtil.setAlarmSetTime(0, context)
    }
}