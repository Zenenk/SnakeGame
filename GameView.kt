package com.example.snakegame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var snake: Snake = Snake()
    private var apple: Apple = Apple(snake.size)
    private var isPaused = false
    private var isGameOver = false

    private var timer = object : CountDownTimer(Long.MAX_VALUE, 500) {
        override fun onTick(p0: Long) {
            if (!isPaused && !isGameOver) {
                if (snake.move() || isOutOfBounds()) {
                    gameOver()
                } else {
                    if (apple.isEaten(snake)) {
                        snake.grow()
                        apple.relocate(width, height, snake)
                    }
                    invalidate()
                }
            }
        }
        override fun onFinish() {}
    }

    init {
        post {
            timer.start()
            apple.relocate(width, height, snake)
            snake = Snake().apply {
                x = (width / 2f / size).toInt() * size
                y = (height / 2f / size).toInt() * size
                body.clear()
                body.add(Pair(x, y))
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBorders(canvas)
        snake.draw(canvas)
        apple.draw(canvas)

        if (isPaused) {
            val paint = Paint()
            paint.textSize = 100f
            paint.color = 0xFFFF0000.toInt()
            canvas.drawText(context.getString(R.string.game_paused), width / 2f - 200, height / 2f, paint)
        }

        if (isGameOver) {
            val paint = Paint()
            paint.textSize = 100f
            paint.color = 0xFFFF0000.toInt()
            val gameOverText = context.getString(R.string.game_over)
            val scoreText = context.getString(R.string.score) + snake.length
            canvas.drawText(gameOverText, width / 2f - 200, height / 2f - 50, paint)
            canvas.drawText(scoreText, width / 2f - 200, height / 2f + 50, paint)
        }
    }

    private fun drawBorders(canvas: Canvas) {
        val borderPaint = Paint()
        borderPaint.color = 0xFF8B0000.toInt()
        val borderWidth = 20f

        canvas.drawRect(0f, 0f, width.toFloat(), borderWidth, borderPaint)
        canvas.drawRect(0f, height - borderWidth, width.toFloat(), height.toFloat(), borderPaint)
        canvas.drawRect(0f, 0f, borderWidth, height.toFloat(), borderPaint)
        canvas.drawRect(width - borderWidth, 0f, width.toFloat(), height.toFloat(), borderPaint)
    }

    private fun isOutOfBounds(): Boolean {
        return snake.x < 0 || snake.x >= width || snake.y < 0 || snake.y >= height
    }

    private fun gameOver() {
        isGameOver = true
        invalidate()
    }

    fun pauseGame() {
        isPaused = true
        invalidate()
    }

    fun resumeGame() {
        if (!isGameOver) {
            isPaused = false
            invalidate()
        }
    }

    fun isGamePaused(): Boolean {
        return isPaused
    }
}
