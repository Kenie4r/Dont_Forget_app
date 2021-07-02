package com.cod3hn.dontforget.`class`

import android.content.Context
import android.preference.PreferenceManager
import com.cod3hn.dontforget.dashBoard

class PreUtil {
    companion object{
        fun getTimerLengtj(context: Context):Int{
            var minutos = tiempo(context).getTiempo()
            return minutos
        }
        private  const val  PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.cod3hn.dontforget.previous_timer_legnth"

        fun getPreviiousTimerLengthSeconds(context: Context):Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds:Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }
        private  const val TIMER_STATE_ID = "com.cod3hn.dontforget.timer_state"

        fun getTimerState(context: Context):dashBoard.TimerState{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0 )
            return dashBoard.TimerState.values()[ordinal]
        }
        fun setTimerState(state: dashBoard.TimerState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }
        private  const val SECONDS_REMAINING = "com.cod3hn.dontforget.seconds_remaining"
        fun getSecondsRemaining(context: Context):Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getLong(SECONDS_REMAINING, 0)
        }

        fun setSecondsRemaining(seconds:Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING, seconds)
            editor.apply()
        }

        private  const val  ALARM_SET_TIME_ID = "com.cod3hn.dontforget.background_time"
        fun getAlarmTime(context: Context):Long{
           val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(ALARM_SET_TIME_ID, 0)
        }
        fun setAlarmSetTime(time:Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID, time)
            editor.apply()
        }
    }
}