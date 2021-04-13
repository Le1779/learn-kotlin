package kevin.le.learnkotlin.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import kevin.le.learnkotlin.R
import kevin.le.learnkotlin.view.components.neumorphism.NeumorphismButton
import kevin.le.learnkotlin.view.components.neumorphism.NeumorphismConstraintLayout
import kevin.le.learnkotlin.view.components.neumorphism.NeumorphismImageButton
import kevin.le.learnkotlin.view.components.neumorphism.shadow.Corner

class NeumorphismActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neumorphism)

        val v1: NeumorphismImageButton = findViewById(R.id.neumorphismImageButton)
        val v2: NeumorphismButton = findViewById(R.id.neumorphismButton)
        val v3: NeumorphismConstraintLayout = findViewById(R.id.neumorphismConstraintLayout)

        val imageButton: NeumorphismImageButton = findViewById(R.id.imageButton)
        imageButton.drawable.corner = Corner.ROUNDED

        val seekBar: SeekBar = findViewById(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                imageButton.drawable.shadowSize = i
                v1.drawable.shadowSize = i
                v2.drawable.shadowSize = i
                v3.drawable.shadowSize = i
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }
}