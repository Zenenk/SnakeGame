package com.example.snakegame

import android.graphics.Canvas
import android.graphics.Paint

class Snake {
    var x = 100f
    var y = 100f
    var size = 100f
    var direct: Direction = Direction.pause
    val body = mutableListOf<Pair<Float, Float>>()

    init {
        body.add(Pair(x, y))
    }

    fun draw(canvas: Canvas) {
        val bodyPaint = Paint()
        bodyPaint.color = 0xFFDA70D6.toInt()

        val headPaint = Paint()
        headPaint.color = 0xFF4B0082.toInt()

        for ((index, segment) in body.withIndex()) {
            val paint = if (index == 0) headPaint else bodyPaint
            canvas.drawRect(segment.first, segment.second, segment.first + size, segment.second + size, paint)
        }
    }

    fun moveTo(d: Direction) {
        if (body.size > 1 && isOppositeDirection(direct, d)) {
            return
        }
        direct = d
    }

    fun move(): Boolean {
        when (direct) {
            Direction.left -> x -= size
            Direction.right -> x += size
            Direction.up -> y -= size
            Direction.down -> y += size
            Direction.pause -> {}
        }

        if (isOccupying(x, y, ignoreHead = true)) {
            return true
        }

        body.add(0, Pair(x, y))
        body.removeAt(body.size - 1)
        return false
    }

    fun grow() {
        body.add(Pair(x, y))
    }

    fun isOccupying(px: Float, py: Float, ignoreHead: Boolean = false): Boolean {
        return body.anyIndexed { index, segment ->
            if (ignoreHead && index == 0) false
            else segment.first == px && segment.second == py
        }
    }

    val length: Int
        get() = body.size

    private fun isOppositeDirection(current: Direction, new: Direction): Boolean {
        return (current == Direction.left && new == Direction.right) ||
                (current == Direction.right && new == Direction.left) ||
                (current == Direction.up && new == Direction.down) ||
                (current == Direction.down && new == Direction.up)
    }
}

inline fun <T> List<T>.anyIndexed(predicate: (index: Int, T) -> Boolean): Boolean {
    for (index in indices) {
        if (predicate(index, this[index])) {
            return true
        }
    }
    return false
}
