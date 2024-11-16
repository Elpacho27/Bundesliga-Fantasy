package hr.ferit.patrikocelic.bundesligafantasy.ui.lineup

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import hr.ferit.patrikocelic.bundesligafantasy.App
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.utils.dp

class FootballFieldDrawable : Drawable() {

    private val lineStrokeWidth = 2.dp
    private val centerRadius = 75.dp
    private val sixteenMetersHeight = 90.dp
    private val fiveMetersHeight = sixteenMetersHeight / 3

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = lineStrokeWidth
    }

    private val fieldPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = App.INSTANCE.getColor(R.color.color_football_field)
    }

    override fun draw(canvas: Canvas) {
        val width = bounds.width()
        val height = bounds.height()

        canvas.drawRect(Rect(0, 0, width, height), fieldPaint)

        // field
        canvas.drawLine(0f + (lineStrokeWidth / 2), 0f + (lineStrokeWidth / 2), width.toFloat() - (lineStrokeWidth / 2), 0f + (lineStrokeWidth / 2), linePaint)
        canvas.drawLine(0f + (lineStrokeWidth / 2), height.toFloat() - (lineStrokeWidth / 2), width.toFloat() - (lineStrokeWidth / 2), height.toFloat() - (lineStrokeWidth / 2), linePaint)
        canvas.drawLine(0f + (lineStrokeWidth / 2), 0f, 0f + (lineStrokeWidth / 2), height.toFloat(), linePaint)
        canvas.drawLine(width.toFloat() - (lineStrokeWidth / 2), 0f, width.toFloat() - (lineStrokeWidth / 2), height.toFloat(), linePaint)

        // center line
        canvas.drawLine(0f, (height / 2).toFloat(), width.toFloat(), (height / 2).toFloat(), linePaint)

        // center circle
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), centerRadius, linePaint)

        // top 16m
        canvas.drawLine((width / 2 - centerRadius), sixteenMetersHeight, (width / 2 + centerRadius), sixteenMetersHeight, linePaint)
        canvas.drawLine((width / 2 - centerRadius), 0f, (width / 2 - centerRadius), sixteenMetersHeight + (lineStrokeWidth / 2), linePaint)
        canvas.drawLine((width / 2 + centerRadius), 0f, (width / 2 + centerRadius), sixteenMetersHeight + (lineStrokeWidth / 2), linePaint)

        // top 5m
        canvas.drawLine((width / 2 - 50.dp), fiveMetersHeight, (width / 2 + 50.dp), fiveMetersHeight, linePaint)
        canvas.drawLine((width / 2 - 50.dp), 0f, (width / 2 - 50.dp), fiveMetersHeight + (lineStrokeWidth / 2), linePaint)
        canvas.drawLine((width / 2 + 50.dp), 0f, (width / 2 + 50.dp), fiveMetersHeight + (lineStrokeWidth / 2), linePaint)

        // bottom 16m
        canvas.drawLine((width / 2 - centerRadius), (height - sixteenMetersHeight), (width / 2 + centerRadius), (height - sixteenMetersHeight), linePaint)
        canvas.drawLine((width / 2 - centerRadius), height.toFloat(), (width / 2 - centerRadius), height.toFloat() - sixteenMetersHeight - (lineStrokeWidth / 2), linePaint)
        canvas.drawLine((width / 2 + centerRadius), height.toFloat(), (width / 2 + centerRadius), height.toFloat() - sixteenMetersHeight - (lineStrokeWidth / 2), linePaint)

        // bottom 5m
        canvas.drawLine((width / 2 - 50.dp), (height - fiveMetersHeight), (width / 2 + 50.dp), (height - fiveMetersHeight), linePaint)
        canvas.drawLine((width / 2 - 50.dp), height.toFloat(), (width / 2 - 50.dp), (height - fiveMetersHeight) - (lineStrokeWidth / 2), linePaint)
        canvas.drawLine((width / 2 + 50.dp), height.toFloat(), (width / 2 + 50.dp), (height - fiveMetersHeight) - (lineStrokeWidth / 2), linePaint)
    }

    override fun setAlpha(alpha: Int) = Unit

    override fun setColorFilter(colorFilter: ColorFilter?) = Unit

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}