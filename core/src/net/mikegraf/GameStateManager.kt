package net.mikegraf

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.util.*

/**
 * Created by Graf on 4/2/2017.
 */
class GameStateManager(private val gameStateFactory: GameStateFactory) {

    val stateStack = Stack<GameState>()
    init {
        pushState(StateType.DEMO)
    }

    fun pushState(type: StateType) {
        val assetManager = AssetManager()
        val state = gameStateFactory.createState(type, assetManager)
        state.loadAssets()
        assetManager.finishLoading()
        state.createAssets()
        stateStack.push(state)
    }

    fun popState() {
        stateStack.pop().dispose()
    }

    fun render(batch: SpriteBatch, deltaTime: Float) {
        stateStack.peek().render(batch, deltaTime)
    }

    fun update(deltaTime: Float) {
        stateStack.peek().update(deltaTime)
    }
}