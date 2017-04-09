package net.mikegraf

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.physics.box2d.World
import net.mikegraf.demo.DemoState
import net.mikegraf.level.LevelState
import net.mikegraf.SlipSliding.Companion.GRAVITY

/**
 * Created by Graf on 4/2/2017.
 */
class GameStateFactory() {
    val levelNumber = 0

    fun createState(type: StateType, assetManager: AssetManager): GameState {
        when(type) {
            StateType.DEMO -> return DemoState(assetManager)
            StateType.LEVEL -> {
                val map = TmxMapLoader().load(getMapPath(levelNumber))
                val world = World(GRAVITY, true)
                return LevelState(map, world, assetManager)
            }
        }
    }

    private fun getMapPath(levelNumber: Int): String {
        val levelNumberToUse = levelNumber + 1
        return "maps/level$levelNumberToUse.tmx"
    }
}
