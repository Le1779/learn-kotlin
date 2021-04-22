package kevin.le.learnkotlin.view.components.neumorphism

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageButton
import kevin.le.learnkotlin.R
import kevin.le.learnkotlin.view.components.neumorphism.shadow.Corner
import kevin.le.learnkotlin.view.components.neumorphism.shadow.State

class NeumorphismImageButton(
        context: Context,
        attrs: AttributeSet? = null
): AppCompatImageButton(context, attrs) {
    val drawable: NeumorphismDrawable

    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.NeumorphismImageButton
        )

        val corner = a.getInt(R.styleable.NeumorphismImageButton_neumorphism_corner, 1)
        var cornerRadius = a.getDimension(R.styleable.NeumorphismImageButton_neumorphism_corner_radius, -1f)

        drawable = NeumorphismDrawable(context).apply {
            this.corner = Corner.values()[corner]
            if (cornerRadius == -1f) {
                cornerRadius = this.corner.radius
            }

            this.radius = cornerRadius
        }
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