package kevin.le.learnkotlin.view.components.neumorphism

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import kevin.le.learnkotlin.view.components.neumorphism.shadow.Corner
import kevin.le.learnkotlin.view.components.neumorphism.shadow.InnerShadow
import kevin.le.learnkotlin.view.components.neumorphism.shadow.OuterShadow
import kevin.le.learnkotlin.view.components.neumorphism.shadow.State
import kotlin.math.min

class NeumorphismDrawable(context: Context) : Drawable() {

    var state: State = State.UP
        set(value) {
            field = value
            invalidateSelf()
        }

    var corner: Corner = Corner.ROUNDED
        set(value) {
            outerShadow.corner = value
            innerShadow.corner = value
            field = value
            invalidateSelf()
        }

    var shadowPadding: Int = 35
        set(value) {
            field = value
            invalidateSelf()
        }

    var shadowSize: Int = 25
        set(value) {
            shadowPadding = value + 10
            outerShadow.centerLayerPath = layerPath
            innerShadow.centerLayerPath = layerPath
            outerShadow.shadowSize = value
            innerShadow.shadowSize = value
            field = value
            invalidateSelf()
        }

    private var outerShadow: OuterShadow = OuterShadow(context)
    private var innerShadow: InnerShadow = InnerShadow(context)
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas) {
        if (state == State.UP) {
            outerShadow.draw(canvas)
        }

        canvas.drawPath(layerPath, paint)

        if (state == State.DOWN) {
            innerShadow.draw(canvas)
        }
    }

    override fun setAlpha(alpha: Int) {
        TODO("Not yet implemented")
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        TODO("Not yet implemented")
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        update()
    }

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#EDECEA")
    }

    private fun update() {
        if (this.bounds.width() == 0 || this.bounds.height() == 0) {
            return
        }

        outerShadow.centerLayerPath = layerPath
        outerShadow.corner = corner
        outerShadow.radius = corner.radius
        outerShadow.bounds = this.bounds
        outerShadow.shadowSize = shadowSize

        innerShadow.centerLayerPath = layerPath
        innerShadow.corner = corner
        innerShadow.radius = corner.radius
        innerShadow.bounds = this.bounds
        innerShadow.shadowSize = shadowSize
        invalidateSelf()
    }

    private fun getCornerRadius(): Float {
        return min(
                bounds.width() / corner.radius,
                bounds.height() / corner.radius
        )
    }

    private val layerPath: Path
        get() {
            val path = Path()
            val left = shadowPadding.toFloat()
            val top = shadowPadding.toFloat()
            val right = this.bounds.width().toFloat() - shadowPadding
            val bottom = this.bounds.height().toFloat() - shadowPadding
            val radius = getCornerRadius()
            path.addRoundRect(
                    left,
                    top,
                    right,
                    bottom,
                    radius,
                    radius,
                    Path.Direction.CW
            )
            path.close()
            return path
        }
}