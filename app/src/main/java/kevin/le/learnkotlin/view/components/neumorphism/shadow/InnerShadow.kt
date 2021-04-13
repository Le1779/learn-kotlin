package kevin.le.learnkotlin.view.components.neumorphism.shadow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect

class InnerShadow(context: Context) : Shadow(context) {

    override fun getDarkShadowBounds(): Rect {
        return Rect((shadowSize/2f).toInt(), (shadowSize/2f).toInt(), bounds.right + shadowSize, bounds.bottom + shadowSize)
    }

    override fun getLightShadowBounds(): Rect {
        return Rect(-shadowSize, -shadowSize, bounds.right - (shadowSize/2f).toInt(), bounds.bottom - (shadowSize/2f).toInt())
    }

    override fun clipCanvas(canvas: Canvas) {
        centerLayerPath?.let { path ->
            canvas.clipPath(path)
        }
    }

    init {
        darkShadowDrawable.setStroke(25, COLOR_DARK_SHADOW)
        lightShadowDrawable.setStroke(25, COLOR_LIGHT_SHADOW)
    }
}