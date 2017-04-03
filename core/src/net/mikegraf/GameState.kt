package net.mikegraf

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable

/**
 * Created by Graf on 4/2/2017.
 */
abstract class GameState(protected val assetManager: AssetManager): Disposable {

    override fun dispose() {
        assetManager.dispose()
    }

    abstract fun loadAssets()

    abstract fun createAssets()

    abstract fun render(batch: SpriteBatch, deltaTime: Float)

    abstract fun update(deltaTime: Float)

}
