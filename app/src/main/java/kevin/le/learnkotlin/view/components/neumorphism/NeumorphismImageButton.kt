package kevin.le.learnkotlin.view.components.neumorphism

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageButton
import kevin.le.learnkotlin.view.components.neumorphism.shadow.Corner
import kevin.le.learnkotlin.view.components.neumorphism.shadow.State

class NeumorphismImageButton(
        context: Context,
        attrs: AttributeSet? = null
): AppCompatImageButton(context, attrs) {
    val drawable: NeumorphismDrawable = NeumorphismDrawable(context)

    init {
        drawable.corner = Corner.CIRCLE
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