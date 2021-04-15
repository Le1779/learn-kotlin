package kevin.le.learnkotlin.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kevin.le.learnkotlin.R
import kevin.le.learnkotlin.view.components.touchpad.BrightAndKelvinTouchpad
import kevin.le.learnkotlin.view.components.touchpad.BrightAndKelvinTouchpad.OnValueChangeListener

class TouchpadActivity : AppCompatActivity() {

    private var bright = 0.6f
    private var kelvin = 0.4f
    var touchpadValueTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touchpad)

        touchpadValueTextView = findViewById(R.id.textView_touchpad_value)

        val touchpad: BrightAndKelvinTouchpad = findViewById(R.id.touchpad)
        touchpad.onValueChangeListener = object : OnValueChangeListener {
            override fun onBrightChange(b: Float) {
                bright = b
                updateTextView()
            }

            override fun onKelvinChange(k: Float) {
                kelvin = k
                updateTextView()
            }
        }
    }

    private fun updateTextView() {
        val b = "%.2f".format(bright)
        val k = "%.2f".format(kelvin)
        touchpadValueTextView!!.text = "Bright: ${b}, Kelvin: ${k}"
    }
}