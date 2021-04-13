package kevin.le.learnkotlin.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import kevin.le.learnkotlin.R
import kevin.le.learnkotlin.view.components.neumorphism.NeumorphismImageButton
import kevin.le.learnkotlin.view.components.neumorphism.shadow.Corner

class NeumorphismActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neumorphism)

        var imageButton: NeumorphismImageButton = findViewById(R.id.imageButton)
        imageButton.drawable.corner = Corner.ROUNDED

        var seekBar: SeekBar = findViewById(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                imageButton.drawable.shadowSize = i
                Log.d("TAG", "value: " + i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }
}