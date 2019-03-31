package com.example.cuatroenraya.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.cuatroenraya.R
import com.example.cuatroenraya.model.TableroConecta4
import com.example.cuatroenraya.utility.setColor
import es.uam.eps.multij.Tablero
import kotlinx.android.synthetic.main.fragment_round.view.*

class ERView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val DEBUG = "ERView"
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var heightOfTile: Float = 0.toFloat()
    private var widthOfTile: Float = 0.toFloat()
    private var radio: Float = 0.toFloat()
    private var board: TableroConecta4? = null
    private var onPlayListener: OnPlayListener? = null
    val NUM_COL = 7
    val NUM_FIL = 6
    interface OnPlayListener {
        fun onPlay(column: Int)
    }

    init {
        backgroundPaint.color = Color.BLACK
        linePaint.strokeWidth = 2f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        widthOfTile = (w / NUM_COL).toFloat()
        heightOfTile = (h / NUM_FIL).toFloat()
        if (widthOfTile < heightOfTile)
            radio = widthOfTile * 0.3f
        else
            radio = heightOfTile * 0.3f
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val boardWidth = width.toFloat()
        val boardHeight = height.toFloat()
        canvas.drawRect(0f, 0f, boardWidth, boardHeight, backgroundPaint)
        drawCircles(canvas, linePaint)
    }

    private fun drawCircles(canvas: Canvas, paint: Paint) {
        var centerRaw: Float
        var centerColumn: Float
        for (i in 0 until NUM_FIL) {
            val pos =  NUM_FIL - i - 1
            centerRaw = heightOfTile * (1 + 2 * pos) / 2f
            for (j in 0 until NUM_COL) {
                centerColumn = widthOfTile * (1 + 2 * j) / 2f
                paint.setColor(board!!, i, j)
                canvas.drawCircle(centerColumn, centerRaw, radio, paint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 500
        val wMode: String
        val hMode: String
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int
        if (widthSize < heightSize) {
            heightSize = widthSize
            height = heightSize
            width = height
        } else {
            widthSize = heightSize
            height = widthSize
            width = height
        }
        setMeasuredDimension(width,height)
    }

    private fun fromEventToJ(event: MotionEvent): Int {
        return (event.x / widthOfTile).toInt()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (onPlayListener == null)
            return super.onTouchEvent(event)
        if (board!!.getEstado() != Tablero.EN_CURSO) {
            Snackbar.make(board_erview,
                R.string.round_already_finished, Snackbar.LENGTH_SHORT).show()
            return super.onTouchEvent(event)
        }
        if (event.action == MotionEvent.ACTION_DOWN) {
            try {
                onPlayListener?.onPlay(fromEventToJ(event))
            } catch (e: Exception) {
                Snackbar.make(board_erview, "Jugada inválida", Snackbar.LENGTH_SHORT).show()
            }
        }
        return true
    }

    fun setOnPlayListener(listener: OnPlayListener) {
        this.onPlayListener = listener
    }
    fun setBoard(board: TableroConecta4) {
        this.board = board
    }

}