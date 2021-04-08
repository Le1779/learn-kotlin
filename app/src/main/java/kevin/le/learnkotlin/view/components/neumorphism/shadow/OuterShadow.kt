package kevin.le.learnkotlin.view.components.neumorphism.shadow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Region
import android.os.Build

class OuterShadow(context: Context) : Shadow(context) {
    override fun getDarkShadowBounds(): Rect {
        return Rect(shadowSize*2, shadowSize*2, bounds.right - shadowSize, bounds.bottom - shadowSize)
    }

    override fun getLightShadowBounds(): Rect {
        return Rect(shadowSize, shadowSize, bounds.right - shadowSize*2, bounds.bottom - shadowSize*2)
    }

    override fun clipCanvas(canvas: Canvas) {
        centerLayerPath?.let { path ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                canvas.clipOutPath(path)
            } else {
                canvas.clipPath(path, Region.Op.DIFFERENCE)
            }
        }
    }

    init {
        darkShadowDrawable.setColor(COLOR_DARK_SHADOW)
        lightShadowDrawable.setColor(COLOR_LIGHT_SHADOW)
    }
}