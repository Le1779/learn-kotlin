package kevin.le.learnkotlin.model

import android.content.Context
import android.content.Intent
import kevin.le.learnkotlin.view.activity.SystemFontActivity

class IntentFactory {
    fun make(context: Context, activityName: String): Intent? {
        return when (activityName) {
            "SystemFont" -> Intent(context, SystemFontActivity::class.java)
            else -> null
        }
    }
}