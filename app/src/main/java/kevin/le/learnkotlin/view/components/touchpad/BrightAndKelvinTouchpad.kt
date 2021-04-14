package kevin.le.learnkotlin.view.components.touchpad

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kevin.le.learnkotlin.model.BlurProvider
import kotlin.math.min
import kotlin.math.roundToInt

class BrightAndKelvinTouchpad(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var bright = 0.6f
    var kelvin = 0.4f
    var radius = 10f
    var offset = 5f
    private var bounds = RectF(0f, 0f, 0f, 0f)
    private var brightRect = RectF(0f, 0f, 0f, 0f)
    private var kelvinRect = RectF(0f, 0f, 0f, 0f)
    private var brightCenter = PointF(0f, 0f)
    private var kelvinCenter = PointF(0f, 0f)
    private var brightPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var kelvinPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mainPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.clipPath(layerPath)
        canvas.drawBitmap(generateBlurBitmap(), 0f, 0f, null)
        canvas.drawPath(layerPath, mainPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        bounds.right = measuredWidth.toFloat()
        bounds.bottom = measuredHeight.toFloat()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                if (x < 0.0 || x > bounds.width()) {
                    return true
                }

                if (y < 0.0 || y > bounds.height()) {
                    return true
                }

                bright = x/bounds.width()
                kelvin = 1 - y/bounds.height()
                invalidate()
            }
        }

        return true
    }

    private fun updateBrightShape() {
        brightCenter.y = bounds.centerY()
        brightCenter.x = bounds.centerX() - bounds.width()/offset*(1 - bright)
        val width = (bounds.width() * bright).roundToInt()
        val height = (bounds.height() * bright).roundToInt()
        brightRect.left = brightCenter.x - width/2
        brightRect.top = brightCenter.y - height/2
        brightRect.right = brightCenter.x + width/2
        brightRect.bottom = brightCenter.y + height/2
    }

    private fun updateKelvinShape() {
        kelvinCenter.y = bounds.centerY()
        kelvinCenter.x = bounds.centerX() + bounds.width()/offset*(1 - kelvin)
        val width = (bounds.width() * kelvin).roundToInt()
        val height = (bounds.height() * kelvin).roundToInt()
        kelvinRect.left = kelvinCenter.x - width/2
        kelvinRect.top = kelvinCenter.y - height/2
        kelvinRect.right = kelvinCenter.x + width/2
        kelvinRect.bottom = kelvinCenter.y + height/2
    }

    private fun generateBlurBitmap(): Bitmap {
        updateBrightShape()
        updateKelvinShape()
        var bitmap = Bitmap.createBitmap(
            this.bounds.width().toInt(),
            this.bounds.height().toInt(),
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        canvas.drawRoundRect(brightRect, radius, radius, brightPaint)
        canvas.drawRoundRect(kelvinRect, radius, radius, kelvinPaint)

        val scale = 0.1f
        bitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).roundToInt(), (bitmap.height * scale).roundToInt(), true)
        bitmap = BlurProvider.blur(context, bitmap)
        bitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width / scale).roundToInt(), (bitmap.height / scale).roundToInt(), true)
        return bitmap
    }

    private val cornerRadius: Float
        get() {
            return min(
                bounds.width() / radius,
                bounds.height() / radius
            )
        }

    private val layerPath: Path
        get() {
            val path = Path()
            val left = 0f
            val top = 0f
            val right = this.bounds.width()
            val bottom = this.bounds.height()
            val radius = cornerRadius
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

    init {
        mainPaint.color = Color.WHITE
        mainPaint.alpha = 85
        brightPaint.color = Color.WHITE
        kelvinPaint.color = Color.rgb(255, 100, 0)
    }
}