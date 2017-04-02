package net.mikegraf

import com.badlogic.gdx.assets.AssetManager

/**
 * Created by Graf on 4/2/2017.
 */
class GameStateFactory {
    fun createState(type: StateType, assetManager: AssetManager): GameState {
        when(type) {
            StateType.DEMO -> return DemoState(assetManager)
        }
    }
}