package net.mikegraf.level

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.World
import net.mikegraf.GameState
import net.mikegraf.PhysicsModel
import net.mikegraf.SlipSliding
import net.mikegraf.SlipSliding.Companion.GAME_HEIGHT
import net.mikegraf.SlipSliding.Companion.GAME_WIDTH
import net.mikegraf.SlipSliding.Companion.VELOCITY_ITERATIONS
import net.mikegraf.SlipSliding.Companion.POSITION_ITERATIONS
import net.mikegraf.level.controller.LevelInput
import net.mikegraf.level.view.BoundedOrthoCamera

class LevelState(private val map: TiledMap, private val world: World, assetManager: AssetManager): GameState(assetManager) {
    val debugMode = false

    private val debugRenderer = Box2DDebugRenderer()
    private val debugCam = OrthographicCamera()
    private val cam = BoundedOrthoCamera(GAME_WIDTH, GAME_HEIGHT)
    private val mapRenderer = OrthogonalTiledMapRenderer(map)
    private lateinit var player: Player

    init {
        setupMainCamera()
        val box2dWidth = SlipSliding.getBox2dValue(GAME_WIDTH)
        val box2dHeight = SlipSliding.getBox2dValue(GAME_HEIGHT)
        debugCam.setToOrtho(false, box2dWidth, box2dHeight)
    }

    private fun setupMainCamera() {
        cam.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT)
        val mapWidth = map.properties.get("width", Int::class.java)
        val mapHeight = map.properties.get("height", Int::class.java)
        val tileWidth = map.properties.get("tilewidth", Int::class.java)
        val tileHeight = map.properties.get("tileheight", Int::class.java)

        val mapWidthInPixels = (mapWidth * tileWidth).toFloat()
        val mapHeightInPixels = (mapHeight * tileHeight).toFloat()

        this.cam.setBounds(0f, 0f, mapWidthInPixels, mapHeightInPixels)
    }

    override fun loadAssets() {
        assetManager.load(PLAYER_TEXTURE_PATH, Texture::class.java)
    }

    override fun createAssets() {
        player = createPlayer()
    }

    override fun render(batch: SpriteBatch, deltaTime: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        cam.moveTo(player.position.x, player.position.y)
        cam.update()

        mapRenderer.setView(cam)
        mapRenderer.render()

        if (debugMode) {
            debugCam.update()
            debugRenderer.render(world, debugCam.combined)
        }

        batch.projectionMatrix = cam.combined

        player.render(batch, deltaTime)
    }

    override fun update(deltaTime: Float) {
        world.step(deltaTime, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
        LevelInput.update()

        player.update(deltaTime)
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        map.dispose()
    }

    private fun createPlayer(): Player {
        val playerTexture: Texture = assetManager.get(PLAYER_TEXTURE_PATH)
        val tempFrames = TextureRegion.split(
                playerTexture,
                playerTexture.width / PLAYER_SHEET_WIDTH,
                playerTexture.height / PLAYER_SHEET_HEIGHT)

        val animationIndex = AnimationIndex()
        animationIndex.add(Player.WALK_LEFT_ANIMATION, Animation(ANIMATION_FRAME_DURATION, tempFrames[0][0]))
        animationIndex.add(Player.WALK_UP_ANIMATION, Animation(ANIMATION_FRAME_DURATION, tempFrames[0][1]))
        animationIndex.add(Player.WALK_RIGHT_ANIMATION, Animation(ANIMATION_FRAME_DURATION, tempFrames[1][0]))
        animationIndex.add(Player.WALK_DOWN_ANIMATION, Animation(ANIMATION_FRAME_DURATION, tempFrames[1][1]))

        val bodyShape = CircleShape()
        bodyShape.radius = SlipSliding.getBox2dValue(PLAYER_RADIUS)
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody

        val body = world.createBody(bodyDef)
        val physModel = PhysicsModel(body)

        return Player(animationIndex, physModel)
    }

    companion object {
        private val PLAYER_TEXTURE_PATH = "player.png"
        private val PLAYER_SHEET_HEIGHT = 2
        private val PLAYER_SHEET_WIDTH = 2
        private val ANIMATION_FRAME_DURATION = .25f
        private val PLAYER_RADIUS = 16f
    }
}
