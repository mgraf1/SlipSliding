package net.mikegraf.level.view

import com.badlogic.gdx.graphics.OrthographicCamera

class BoundedOrthoCamera(vWidth: Float, vHeight: Float) : OrthographicCamera() {

    private var minX: Float = 0f
    private var maxX: Float = 0f
    private var minY: Float = 0f
    private var maxY: Float = 0f

    init {
        this.setToOrtho(false, vWidth, vHeight)
    }

    fun setBounds(xMin: Float, yMin: Float, xMax: Float, yMax: Float) {
        return when {
            xMin > xMax ->  throw IllegalArgumentException("Minimum X cannot be greater than maximum X.")
            yMin > yMax ->  throw IllegalArgumentException("Minimum Y cannot be greater than maximum Y.")
            xMin < 0 ->     throw IllegalArgumentException("Minimum X cannot be less than 0.")
            yMin < 0 ->     throw IllegalArgumentException("Minimum Y cannot be less than 0.")
            else -> {
                minX = xMin + viewportWidth / 2
                maxX = xMax - viewportWidth / 2
                minY = yMin + viewportHeight / 2
                maxY = yMax - viewportHeight / 2
            }
        }
    }

    fun moveTo(x: Float, y: Float) {
        val xVal = when {
            x < minX -> minX
            x > maxX -> maxX
            else ->     x
        }

        val yVal = when {
            y < minY -> minY
            y > maxY -> maxY
            else ->     y
        }

        this.position.set(xVal, yVal, 0f)
    }
}
