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
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import net.mikegraf.GameState
import net.mikegraf.PhysicsModel
import net.mikegraf.SlipSliding
import net.mikegraf.SlipSliding.Companion.GAME_HEIGHT
import net.mikegraf.SlipSliding.Companion.GAME_WIDTH
import net.mikegraf.SlipSliding.Companion.VELOCITY_ITERATIONS
import net.mikegraf.SlipSliding.Companion.POSITION_ITERATIONS
import net.mikegraf.level.controller.LevelInput
import net.mikegraf.level.model.MyContactListener
import net.mikegraf.level.view.BoundedOrthoCamera

class LevelState(private val map: TiledMap, private val world: World, assetManager: AssetManager): GameState(assetManager) {
    val debugMode = false

    private val debugRenderer = Box2DDebugRenderer()
    private val debugCam = BoundedOrthoCamera(SlipSliding.getBox2dValue(GAME_WIDTH), SlipSliding.getBox2dValue(GAME_HEIGHT))
    private val cam = BoundedOrthoCamera(GAME_WIDTH, GAME_HEIGHT)
    private val mapRenderer = OrthogonalTiledMapRenderer(map)
    private lateinit var player: Player

    init {
        world.setContactListener(MyContactListener())

        val mapWidth = map.properties.get("width", Int::class.java)
        val mapHeight = map.properties.get("height", Int::class.java)
        val tileWidth = map.properties.get("tilewidth", Int::class.java)
        val tileHeight = map.properties.get("tileheight", Int::class.java)

        val mapWidthInPixels = (mapWidth * tileWidth).toFloat()
        val mapHeightInPixels = (mapHeight * tileHeight).toFloat()
        val b2dMapWidthInPixels = SlipSliding.getBox2dValue(mapWidthInPixels)
        val b2dMapHeightInPixels = SlipSliding.getBox2dValue(mapHeightInPixels)

        cam.setBounds(0f, 0f, mapWidthInPixels, mapHeightInPixels)
        debugCam.setBounds(0f, 0f, b2dMapWidthInPixels, b2dMapHeightInPixels)

        placeBorder(b2dMapWidthInPixels, b2dMapHeightInPixels)
    }

    override fun loadAssets() {
        assetManager.load(PLAYER_TEXTURE_PATH, Texture::class.java)
    }

    override fun createAssets() {
        player = createPlayer()
    }

    override fun render(batch: SpriteBatch, deltaTime: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val playerX = player.position.x
        val playerY = player.position.y
        cam.moveTo(playerX, playerY)
        debugCam.moveTo(SlipSliding.getBox2dValue(playerX), SlipSliding.getBox2dValue(playerY))

        cam.update()
        if (debugMode) {
            debugCam.update()
        }

        mapRenderer.setView(cam)
        mapRenderer.render()

        if (debugMode) {
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

    private fun placeBorder(mapWidthInPixels: Float, mapHeightInPixels: Float) {
        val fDef = FixtureDef()
        val bDef = BodyDef()

        val eShape = EdgeShape()
        fDef.shape = eShape

        // Left wall.
        eShape.set(0f, 0f, 0f, mapHeightInPixels)
        bDef.position.set(0f, 0f)
        val leftWall = world.createBody(bDef)
        leftWall.createFixture(fDef)

        // Bottom wall.
        eShape.set(0f, 0f, mapWidthInPixels, 0f)
        bDef.position.set(0f, 0f)
        val bottomWall = world.createBody(bDef)
        bottomWall.createFixture(fDef)

        // Right wall.
        eShape.set(mapWidthInPixels, 0f, mapWidthInPixels, mapHeightInPixels)
        bDef.position.set(0f, 0f)
        val rightWall = world.createBody(bDef)
        rightWall.createFixture(fDef)

        // Top wall.
        eShape.set(0f, mapHeightInPixels, mapWidthInPixels, mapHeightInPixels)
        bDef.position.set(0f, 0f)
        val topWall = world.createBody(bDef)
        topWall.createFixture(fDef)
    }

    private fun createPlayer(): Player {
        val animationIndex = createPlayerAnimationIndex()
        val region = animationIndex.getKeyFrame(0f)
        val renderWidth = region.regionWidth.toFloat()
        val renderHeight = region.regionHeight.toFloat()

        val physModel = createPlayerPhysicsModel(renderWidth, renderHeight)

        return Player(animationIndex, physModel)
    }

    private fun createPlayerPhysicsModel(renderWidth: Float, renderHeight: Float): PhysicsModel {
        val bodyShape = CircleShape()
        bodyShape.radius = SlipSliding.getBox2dValue(PLAYER_RADIUS)
        val bodyDef = BodyDef()
        val fixtureDef = FixtureDef()

        bodyDef.type = BodyDef.BodyType.DynamicBody
        fixtureDef.shape = bodyShape

        val body = world.createBody(bodyDef)
        body.createFixture(fixtureDef)

        val physModel = PhysicsModel(body, renderWidth, renderHeight)
        bodyShape.dispose()

        return physModel
    }

    private fun createPlayerAnimationIndex(): AnimationIndex {
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

        return animationIndex
    }

    companion object {
        private val PLAYER_TEXTURE_PATH = "player.png"
        private val PLAYER_SHEET_HEIGHT = 2
        private val PLAYER_SHEET_WIDTH = 2
        private val ANIMATION_FRAME_DURATION = .25f
        private val PLAYER_RADIUS = 16f
    }
}
