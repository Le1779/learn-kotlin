package kevin.le.learnkotlin.view.components.neumorphism.shadow

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import kevin.le.learnkotlin.model.BlurProvider
import kotlin.math.min

abstract class Shadow(
        private val context: Context,
        var shadowSize: Int = 25
) {

    var corner: Corner = Corner.ROUNDED
        set(value) {
            if (value == corner) {
                return
            }

            regenerate()
            field = value
        }

    var radius: Float = corner.radius
        set(value) {
            if (value == radius) {
                return
            }

            regenerate()
            field = value
        }

    var bounds: Rect = Rect(0, 0, 1, 1)
        set(value) {
            if (value == bounds) {
                return
            }

            field = value
            regenerate()
        }
    var centerLayerPath: Path? = null
    var darkShadowDrawable: GradientDrawable = GradientDrawable()
    var lightShadowDrawable: GradientDrawable = GradientDrawable()
    private var darkShadowBmp: Bitmap? = null
    private var lightShadowBmp: Bitmap? = null

    abstract fun getDarkShadowBounds(): Rect
    abstract fun getLightShadowBounds(): Rect
    abstract fun clipCanvas(canvas: Canvas)

    open fun draw(canvas: Canvas) {
        canvas.save()
        clipCanvas(canvas)
        darkShadowBmp?.let { bmp ->
            canvas.drawBitmap(bmp, 0f, 0f, null)
        }

        lightShadowBmp?.let { bmp ->
            canvas.drawBitmap(bmp, 0f, 0f, null)
        }
        canvas.restore()
    }

    private fun regenerate() {
        darkShadowDrawable.bounds = getDarkShadowBounds()
        lightShadowDrawable.bounds = getLightShadowBounds()
        darkShadowDrawable.cornerRadius = getCornerRadius(darkShadowDrawable.bounds)
        lightShadowDrawable.cornerRadius = getCornerRadius(lightShadowDrawable.bounds)

        darkShadowBmp = generateBlurBitmapFromDrawable(darkShadowDrawable)
        lightShadowBmp = generateBlurBitmapFromDrawable(lightShadowDrawable)
    }
    private fun getCornerRadius(bounds: Rect): Float {
        return min(
                bounds.width() / radius,
                bounds.height() / radius
        )
    }

    private fun generateBlurBitmapFromDrawable(drawable: Drawable?): Bitmap {
        val bitmap = Bitmap.createBitmap(
                this.bounds.width(),
                this.bounds.height(),
                Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable!!.draw(canvas)
        return BlurProvider.blur(context, bitmap, shadowSize)
    }

    companion object {
        val COLOR_LIGHT_SHADOW = Color.argb(150, 255, 255, 255)
        val COLOR_DARK_SHADOW = Color.argb(40, 0, 0, 0)
    }
}