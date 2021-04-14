package kevin.le.learnkotlin.model

import android.content.Context
import android.content.Intent
import kevin.le.learnkotlin.view.activity.NeumorphismActivity
import kevin.le.learnkotlin.view.activity.SystemFontActivity
import kevin.le.learnkotlin.view.activity.TouchpadActivity

class IntentFactory {
    fun make(context: Context, activityName: String): Intent? {
        return when (activityName) {
            "SystemFont" -> Intent(context, SystemFontActivity::class.java)
            "Neumorphism" -> Intent(context, NeumorphismActivity::class.java)
            "Touchpad" -> Intent(context, TouchpadActivity::class.java)
            else -> null
        }
    }
}