package kevin.le.learnkotlin.view.components.neumorphism

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import kevin.le.learnkotlin.R
import kevin.le.learnkotlin.view.components.neumorphism.shadow.Corner
import kevin.le.learnkotlin.view.components.neumorphism.shadow.State

class NeumorphismConstraintLayout(
        context: Context,
        attrs: AttributeSet? = null
): ConstraintLayout(context, attrs) {

    val drawable: NeumorphismDrawable

    init {
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.NeumorphismConstraintLayout
        )

        val state = a.getInt(R.styleable.NeumorphismConstraintLayout_neumorphism_state, 0)
        val corner = a.getInt(R.styleable.NeumorphismConstraintLayout_neumorphism_corner, 1)
        val cornerRadius = a.getDimension(R.styleable.NeumorphismConstraintLayout_neumorphism_corner_radius, 2f)

        drawable = NeumorphismDrawable(context).apply {
            this.state = State.values()[state]
            this.corner = Corner.values()[corner]
            this.radius = cornerRadius
        }
        background = drawable
    }
}