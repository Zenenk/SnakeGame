package com.example.snakegame

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.random.Random

class Apple(private val gridSize: Float) {
    var x: Float = 0f
    var y: Float = 0f

    fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = 0xFF8B0000.toInt()
        canvas.drawRect(x, y, x + gridSize, y + gridSize, paint)
    }

    fun relocate(viewWidth: Int, viewHeight: Int, snake: Snake) {
        do {
            x = (Random.nextInt(viewWidth / gridSize.toInt())) * gridSize
            y = (Random.nextInt(viewHeight / gridSize.toInt())) * gridSize
        } while (snake.isOccupying(x, y))
    }

    fun isEaten(snake: Snake): Boolean {
        return snake.x == x && snake.y == y
    }
}
