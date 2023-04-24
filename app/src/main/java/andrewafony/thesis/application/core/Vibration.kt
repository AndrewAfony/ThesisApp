package andrewafony.thesis.application.core

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

interface Vibration {

    fun buttonClick()

    class Base(private val context: Context): Vibration {

        private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        override fun buttonClick() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_HEAVY_CLICK))
            } else {
                vibrator.vibrate(100)
            }
        }
    }
}