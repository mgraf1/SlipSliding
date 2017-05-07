package net.mikegraf

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2


class SlipSliding(val inputHandler: InputAdapter) : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var gameStateManager: GameStateManager
    private var accumulator: Float = 0f

    override fun create() {
        batch = SpriteBatch()
        val gameStateFactory = GameStateFactory()
        gameStateManager = GameStateManager(gameStateFactory)
        Gdx.input.inputProcessor = inputHandler
    }

    override fun render() {
        accumulator += Gdx.graphics.deltaTime
        while (accumulator >= FRAME_RATE) {
            accumulator -= FRAME_RATE
            gameStateManager.update(FRAME_RATE)
            gameStateManager.render(batch, FRAME_RATE)
        }
    }

    override fun dispose() {
        batch.dispose()
    }

    companion object {
        val FRAME_RATE = 1/60f
        val GAME_WIDTH = 320f
        val GAME_HEIGHT = 240f
        val PIXELS_PER_METER = 100f
        val GRAVITY = Vector2(0f, 0f)
        val VELOCITY_ITERATIONS = 6
        val POSITION_ITERATIONS = 2

        fun getBox2dCoords(vectorToPopulate: Vector2, renderCoords: Vector2) {
            vectorToPopulate.x = renderCoords.x / PIXELS_PER_METER
            vectorToPopulate.y = renderCoords.y / PIXELS_PER_METER
        }

        fun getRenderCoords(vectorToPopulate: Vector2, box2dCoords: Vector2) {
            vectorToPopulate.x = box2dCoords.x * PIXELS_PER_METER
            vectorToPopulate.y = box2dCoords.y * PIXELS_PER_METER
        }

        fun getBox2dValue(value: Float): Float {
            return value / PIXELS_PER_METER
        }

        fun getRenderValue(value: Float): Float {
            return value * PIXELS_PER_METER
        }
    }
}
