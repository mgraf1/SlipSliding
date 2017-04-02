package net.mikegraf

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

/**
 * Created by Graf on 4/2/2017.
 */
class DemoState(assetManager: AssetManager): GameState(assetManager) {
    private lateinit var img: Texture

    override fun loadAssets() {
        assetManager.load(IMAGE_PATH, Texture::class.java)
    }

    override fun createAssets() {
        img = assetManager.get(IMAGE_PATH)
    }

    override fun render(batch: SpriteBatch, deltaTime: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(img, 0f, 0f)
        batch.end()
    }

    override fun update(deltaTime: Float) {

    }

    companion object {
        val IMAGE_PATH = "badlogic.jpg"
    }
}