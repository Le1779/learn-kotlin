package kevin.le.learnkotlin.model

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import kotlin.math.roundToInt

object BlurProvider {
    @JvmOverloads
    fun blur(context: Context?, source: Bitmap, radius: Int = 25): Bitmap {
        val width = source.width.toFloat().roundToInt()
        val height = source.height.toFloat().roundToInt()
        val destination = Bitmap.createScaledBitmap(source, width, height, false)
        val renderScript = RenderScript.create(context)
        val input = Allocation.createFromBitmap(renderScript, destination)
        val output = Allocation.createTyped(renderScript, input.type)
        val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(
            renderScript,
            Element.U8_4(renderScript)
        )
        scriptIntrinsicBlur.setInput(input)
        scriptIntrinsicBlur.setRadius(radius.toFloat())
        scriptIntrinsicBlur.forEach(output)
        output.copyTo(destination)
        renderScript.destroy()
        return destination
    }
}