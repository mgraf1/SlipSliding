package net.mikegraf.Level

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import net.mikegraf.GameState
import net.mikegraf.SlipSliding
import net.mikegraf.SlipSliding.Companion.GAME_HEIGHT
import net.mikegraf.SlipSliding.Companion.GAME_WIDTH
import net.mikegraf.SlipSliding.Companion.VELOCITY_ITERATIONS
import net.mikegraf.SlipSliding.Companion.POSITION_ITERATIONS

/**
 * Created by Graf on 4/2/2017.
 */
class LevelState(private val map: TiledMap, private val world: World, assetManager: AssetManager): GameState(assetManager) {
    val debugMode = false

    private val debugRenderer = Box2DDebugRenderer()
    private val debugCam = OrthographicCamera()
    private val cam = OrthographicCamera()
    private val mapRenderer = OrthogonalTiledMapRenderer(map)

    init {
        cam.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT)
        debugCam.setToOrtho(false, SlipSliding.getBox2dCoords(GAME_WIDTH), SlipSliding.getBox2dCoords(GAME_HEIGHT))
    }

    override fun loadAssets() {

    }

    override fun createAssets() {

    }

    override fun render(batch: SpriteBatch, deltaTime: Float) {
        cam.position.set(50f, 50f, 0f)
        cam.update()
        if (debugMode) debugCam.update()

        mapRenderer.setView(cam)
        mapRenderer.render()
        if (debugMode) debugRenderer.render(world, debugCam.combined)
    }

    override fun update(deltaTime: Float) {
        world.step(deltaTime, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        map.dispose()
    }
}
