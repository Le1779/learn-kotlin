package kevin.le.learnkotlin.view.components.neumorphism

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import kevin.le.learnkotlin.view.components.neumorphism.shadow.State

class NeumorphismConstraintLayout(
        context: Context,
        attrs: AttributeSet? = null
): ConstraintLayout(context, attrs) {

    private val drawable: NeumorphismDrawable = NeumorphismDrawable(context)

    init {
        drawable.state = State.DOWN
        background = drawable
    }
}