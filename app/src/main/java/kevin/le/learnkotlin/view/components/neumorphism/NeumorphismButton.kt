package kevin.le.learnkotlin.view.components.neumorphism

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import kevin.le.learnkotlin.R
import kevin.le.learnkotlin.view.components.neumorphism.shadow.State

class NeumorphismButton(
        context: Context,
        attrs: AttributeSet? = null
): AppCompatButton(context, attrs) {

    val drawable: NeumorphismDrawable = NeumorphismDrawable(context)

    init {
        background = drawable
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                drawable.state = State.DOWN
            MotionEvent.ACTION_UP ->
                drawable.state = State.UP
        }
        return super.onTouchEvent(event)
    }
}