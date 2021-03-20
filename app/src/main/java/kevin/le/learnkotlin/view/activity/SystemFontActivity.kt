package kevin.le.learnkotlin.view.activity

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import kevin.le.learnkotlin.R
import java.io.File

class SystemFontActivity : AppCompatActivity() {

    lateinit var previewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_font)
        getSystemFontList()
        initPreviewTextView()
        initSpinner()
    }

    private fun initPreviewTextView() {
        previewTextView = findViewById(R.id.textView_preview)
    }

    private fun initSpinner() {
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            getSystemFontList()
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val selectedItem = parent!!.selectedItem.toString()
                val typeFace = Typeface.createFromFile("/system/fonts/${selectedItem}")
                previewTextView.typeface = typeFace
            }
        }
    }

    private fun getSystemFontList(): ArrayList<String> {
        val list = ArrayList<String>()
        val fontFolder = File("/system/fonts/")

        fontFolder.listFiles()?.forEach {
            list.add(it.name)
        }

        return list
    }
}