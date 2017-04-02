package net.mikegraf

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch


class SlipSliding : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var gameStateManager: GameStateManager
    private var accumulator: Float = 0f

    override fun create() {
        batch = SpriteBatch()
        val gameStateFactory = GameStateFactory()
        gameStateManager = GameStateManager(gameStateFactory)
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
        val FRAME_RATE: Float = 1/60f
    }
}
