package kevin.le.learnkotlin.view.components.touchpad

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BrightAndKelvinTouchpad : View {
    private var isEnable = false
    private var bright = 0.5f
    private var temperature = 0.5f
    private var callback: Callback? = null

    interface Callback {
        fun onDataChange(bright: Float, temperature: Float)
    }

    fun setEnable(enable: Boolean) {
        isEnable = enable
        invalidate()
    }

    fun setBright(bright: Float) {
        this.bright = 1 - bright
        invalidate()
    }

    fun setTemperature(temperature: Float) {
        this.temperature = temperature
        invalidate()
    }

    private var viewHeight = 0
    private var viewWidth = 0
    private var widthMargin = 0f
    private var heightMargin = 0f
    private var viewRadius = 0f
    private var backgroundRect: RectF? = null
    private var backgroundPaint: Paint? = null
    private var backgroundRadius = 0f
    private val backgroundBitmap: Bitmap? = null
    private var touchPoint: PointF? = null
    private var controlRadius = 0f
    private var controlPoint: PointF? = null
    private var controlPaint: Paint? = null
    private var shadowPaint: Paint? = null
    private var shadowBitmap: Bitmap? = null
    private var glowPaint: Paint? = null
    private var leftGlowBitmap: Bitmap? = null
    private var rightGlowBitmap: Bitmap? = null
    private var leftGlowRect: RectF? = null
    private var rightGlowRect: RectF? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewHeight = measuredHeight
        viewWidth = measuredWidth
        resize()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawShadow(canvas)
        drawGlow(canvas)
        drawBackground(canvas)
        if (isEnable) {
            drawPoint(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnable) {
            return true
        }
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchPoint!!.x = x.toFloat()
                touchPoint!!.y = y.toFloat()
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                temperature += (x - touchPoint!!.x) / 5f / viewRadius * 2f
                bright += (y - touchPoint!!.y) / 5f / viewRadius * 2f
                if (temperature < 0f) {
                    temperature = 0f
                }
                if (temperature > 1f) {
                    temperature = 1f
                }
                if (bright < 0f) {
                    bright = 0f
                }
                if (bright > 1f) {
                    bright = 1f
                }
                if (callback != null) {
                    callback!!.onDataChange(1 - bright, temperature)
                }
                touchPoint!!.x = x.toFloat()
                touchPoint!!.y = y.toFloat()
                setLeftGlowRect()
                setRightGlowRect()
                setLeftGlowBitmap()
                setRightGlowBitmap()
                invalidate()
            }
        }
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun init() {
        backgroundRect = RectF()
        backgroundPaint = Paint()
        backgroundPaint!!.color = Color.argb(25, 255, 255, 255)
        controlPaint = Paint()
        controlPaint!!.style = Paint.Style.STROKE
        controlPaint!!.color = Color.WHITE
        controlPoint = PointF()
        touchPoint = PointF()
        shadowPaint = Paint()
        glowPaint = Paint()
        leftGlowRect = RectF()
        rightGlowRect = RectF()
    }

    private fun resize() {
        widthMargin = viewWidth * 0.05f
        heightMargin = viewHeight * 0.1f
        viewRadius =
            if (viewHeight < viewWidth) viewHeight / 2f - heightMargin else viewWidth / 2f - widthMargin
        controlPoint!!.x = viewWidth / 2f
        controlPoint!!.y = viewHeight / 2f
        controlRadius = viewRadius / 30f
        controlPaint!!.strokeWidth = controlRadius / 5f
        backgroundRadius = viewWidth * 0.05f
        setBackgroundRect()
        setLeftGlowRect()
        setRightGlowRect()
        setShadowBitmap()
        setLeftGlowBitmap()
        setRightGlowBitmap()
    }

    private fun setBackgroundRect() {
        backgroundRect!!.top = heightMargin
        backgroundRect!!.bottom = viewHeight - heightMargin
        backgroundRect!!.left = viewWidth / 2f - viewRadius
        backgroundRect!!.right = viewWidth / 2f + viewRadius
    }

    private fun setLeftGlowRect() {
        val n = backgroundRadius / 2 * temperature
        leftGlowRect!!.top = heightMargin - n
        leftGlowRect!!.bottom = viewHeight - heightMargin + n
        leftGlowRect!!.left = viewWidth / 2f - viewRadius - n
        leftGlowRect!!.right =
            leftGlowRect!!.left + (viewRadius * 2 - backgroundRadius * 3) * temperature + backgroundRadius * 2 + n
    }

    private fun setRightGlowRect() {
        val n = backgroundRadius / 2 * (1 - temperature)
        rightGlowRect!!.top = heightMargin - n
        rightGlowRect!!.bottom = viewHeight - heightMargin + n
        rightGlowRect!!.right = viewWidth / 2f + viewRadius + n
        rightGlowRect!!.left =
            rightGlowRect!!.right - (viewRadius * 2 - backgroundRadius * 3) * (1 - temperature) - backgroundRadius * 2 - n
    }

    private fun drawBackground(canvas: Canvas) {
        //canvas.drawCircle(viewWidth/2f, viewHeight/2f, viewRadius, backgroundPaint);
        canvas.drawRoundRect(
            backgroundRect!!,
            backgroundRadius,
            backgroundRadius,
            backgroundPaint!!
        )
    }

    private fun drawPoint(canvas: Canvas) {
        controlPoint!!.x = viewWidth / 2f - viewRadius + viewRadius * 2f * temperature
        controlPoint!!.y = heightMargin + viewRadius * 2f * bright
        canvas.drawCircle(controlPoint!!.x, controlPoint!!.y, controlRadius, controlPaint!!)
    }

    private fun drawShadow(canvas: Canvas) {
        canvas.drawBitmap(shadowBitmap!!, 0f, 0f, shadowPaint)
    }

    private fun drawGlow(canvas: Canvas) {
        canvas.drawBitmap(leftGlowBitmap!!, 0f, 0f, glowPaint)
        canvas.drawBitmap(rightGlowBitmap!!, 0f, 0f, glowPaint)
    }

    private fun setShadowBitmap() {
        shadowBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(shadowBitmap!!)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setShadowLayer(widthMargin, 0f, widthMargin / 2, Color.argb(50, 0, 0, 0))
        paint.color = Color.argb(255, 255, 255, 255)
        canvas.drawRoundRect(backgroundRect!!, backgroundRadius, backgroundRadius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        paint.setShadowLayer(0f, 0f, 0f, 0x00000000)
        canvas.drawRoundRect(backgroundRect!!, backgroundRadius, backgroundRadius, paint)
    }

    private fun setLeftGlowBitmap() {
        leftGlowBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(leftGlowBitmap!!)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setShadowLayer(widthMargin * (1 - bright + 0.1f), 0f, 0f, -0xf55a6)
        paint.color = -0xf55a6
        canvas.drawRoundRect(leftGlowRect!!, backgroundRadius, backgroundRadius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        paint.setShadowLayer(0f, 0f, 0f, 0x00000000)
        canvas.drawRoundRect(backgroundRect!!, backgroundRadius, backgroundRadius, paint)
    }

    private fun setRightGlowBitmap() {
        rightGlowBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(rightGlowBitmap!!)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setShadowLayer(widthMargin * (1 - bright + 0.1f), 0f, 0f, -0x1)
        paint.color = -0x1
        canvas.drawRoundRect(rightGlowRect!!, backgroundRadius, backgroundRadius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        paint.setShadowLayer(0f, 0f, 0f, 0x00000000)
        canvas.drawRoundRect(backgroundRect!!, backgroundRadius, backgroundRadius, paint)
    }
}